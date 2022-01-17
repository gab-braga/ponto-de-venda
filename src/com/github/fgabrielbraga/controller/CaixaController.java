package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.model.*;
import com.github.fgabrielbraga.model.dao.*;
import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.SearchGuide;
import com.github.fgabrielbraga.controller.util.Helper;
import com.github.fgabrielbraga.controller.util.Validator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.github.fgabrielbraga.view.PesquisarCliente;
import com.github.fgabrielbraga.view.PesquisarProduto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CaixaController implements Initializable, SearchGuide {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnAddClient;

    @FXML
    private Button btnAddItem;

    @FXML
    private TextField fieldClient;

    @FXML
    private TextField fieldQuantity;

    @FXML
    private TextField fieldValueReceived;

    @FXML
    private TextField fieldProductCode;

    @FXML
    private TextField fieldProductDescription;

    @FXML
    private Text textDate;

    @FXML
    private Text textOperator;

    @FXML
    private Text textExchange;

    @FXML
    private Text textProductDescription;

    @FXML
    private Text textValueUnitary;

    @FXML
    private Text textValueBuy;

    @FXML
    private Text textValueTotal;

    @FXML
    private Text textNumberItems;

    @FXML
    private TableView<Item> tableItems;

    @FXML
    private TableColumn<Item, String> tableColumnDescription;

    @FXML
    private TableColumn<Item, Integer> tableColumnQuantity;

    @FXML
    private MenuItem tableItemRemove;

    private final Double INITIAL_VALUE_DOUBLE = 0.0;
    private final String DEFAULT_TEXT = "-";
    private final Integer FIRST = 0;

    private Sale sale;

    private Item item;

    public CaixaController() {
        sale = new Sale(new ArrayList<Item>());
        sale.setOperator(Access.getOperator());
        sale.setDate(Helper.getCurrentDate());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillInfoBar();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnAddItem.setOnMouseClicked(click -> {
            searchAndSetItem();
        });

        btnAddClient.setOnMouseClicked(click -> {
            searchAndSetClient();
        });

        fieldClient.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addNewClient();
        });

        fieldQuantity.setOnKeyReleased(keyEvent -> {
            setItemQuantity();
        });

        fieldValueReceived.setOnKeyReleased(keyEvent -> {
            defineReceivedValue();
        });

        fieldProductDescription.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addNewItem();
        });

        fieldProductCode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                addNewItem();
        });

        tableItems.setOnMouseClicked(click -> {
            changeItem();
        });

        tableItemRemove.setOnAction(action -> {
            removeItem();
        });

        tableItems.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE)
                removeItem();
        });

        btnSubmit.setOnMouseClicked(click -> {
            sell();
        });

        Helper.addTextLimiter(fieldQuantity, 3);
        Helper.addTextLimiter(fieldValueReceived, 9);
        Helper.addTextLimiter(fieldClient, 50);
        Helper.addTextLimiter(fieldProductDescription, 80);
        Helper.addTextLimiter(fieldProductCode, 20);
    }

    @Override
    public void returnData(Object o) {
        if(o instanceof Product) {
            Product product = (Product) o;
            setItem(new Item(product));
            sale.addItemToList(getItem());
            showItem();
        }
        else if(o instanceof Client) {
            Client client = (Client) o;
            sale.setCliente(client);
            showClient();
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    private void showItem() {
        if(Validator.validateObject(item)) {
            textProductDescription.setText(item.getProduto().getDescription());
            textValueUnitary.setText(Helper.formatNumber(item.getProduto().getPrice()));
            fieldQuantity.setText(item.getQuantity().toString());
            fieldProductCode.setText(item.getProduto().getCode().toString());
            fieldProductDescription.setText(item.getProduto().getDescription());
            fillTableItems();
            calculateValues();
        }
    }

    private void resetItem() {
        item = null;
        textProductDescription.setText(DEFAULT_TEXT);
        textValueUnitary.setText(DEFAULT_TEXT);
        fieldQuantity.clear();
        textValueTotal.setText(DEFAULT_TEXT);
        fieldProductCode.clear();
        fieldProductDescription.clear();
        fillTableItems();
        calculateValues();
    }

    private void fillTableItems() {
        tableColumnDescription.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescription()));
        tableColumnQuantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
        ObservableList<Item> items = FXCollections.observableArrayList(sale.getItems());
        tableItems.setItems(items);
        tableItems.refresh();
        textNumberItems.setText(Integer.toString(sale.getItems().size()));
    }

    private void calculateValues() {
        calculateTotalItemValue();
        calculateTotalSale();
        calculateExchangeValue();
    }

    private void calculateTotalItemValue() {
        if(Validator.validateObject(item)) {
            Double totalValue = item.getTotalValue();
            textValueTotal.setText(Helper.formatNumber(totalValue));
        }
    }

    private void calculateTotalSale() {
        if(Validator.validateObject(sale)) {
            Double totalSale = INITIAL_VALUE_DOUBLE;
            for(Item item : sale.getItems()) {
                totalSale += item.getTotalValue();
            }
            sale.setValue(Helper.roundNumberTwoDecimalPlaces(totalSale));
            textValueBuy.setText(Helper.formatNumber(sale.getValue()));
        }
    }

    private void calculateExchangeValue() {
        Double receivedValue = retrieveReceivedValue();
        if(Validator.validateObject(receivedValue)) {
            Double totalValue = sale.getValue();
            Double exchange = receivedValue - totalValue;
            if(exchange > INITIAL_VALUE_DOUBLE) {
                textExchange.setText(Helper.formatNumber(exchange));
            }
            else {
                textExchange.setText(Helper.formatNumber(INITIAL_VALUE_DOUBLE));
            }
        }
    }

    private void showClient() {
        fieldClient.setText(sale.getCliente().getName());
    }

    private void fillInfoBar() {
        if(sale.getOperator() != null)
            textOperator.setText(sale.getOperator().getName());
        if(sale.getDate() != null)
            textDate.setText(Helper.formatDate(sale.getDate()));
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    private void addNewItem() {
        if(isSearchedItem()) {
            searchAndSetItem();
        }
        else {
            fecthAndSeItem();
        }
    }

    private boolean isSearchedItem() {
        return fieldProductCode.getText().isBlank();
    }

    private void searchAndSetItem() {
        String productDescription = fieldProductDescription.getText();
        PesquisarProduto pesquisarProduto = new PesquisarProduto(this, productDescription);
        pesquisarProduto.start(new Stage());
    }

    private void fecthAndSeItem() {
        Long productCode = retrieveProductCode();
        if(Validator.validateObject(productCode)) {
            ProductDAO dao = new ProductDAO();
            List<Product> products = dao.selectProductByCode(productCode);
            if(products.isEmpty()) {
                AlertBox.unregisteredProduct();
            }
            else {
                Product product = products.get(FIRST);
                item = new Item(product);
                sale.addItemToList(item);
                showItem();
                fieldQuantity.requestFocus();
            }
        }
    }

    private Long retrieveProductCode() {
        String productCode = fieldProductCode.getText();
        if(Validator.validateInteger(productCode)) {
            return Long.parseLong(productCode);
        }
        else {
            AlertBox.onlyNumbers();
            return null;
        }
    }

    private void addNewClient() {
        if(isSearchedClient()) {
            searchAndSetClient();
        }
        else {
            fecthAndSeClient();
        }
        fillTableItems();
    }

    private boolean isSearchedClient() {
        return fieldClient.getText().isBlank();
    }

    private void searchAndSetClient() {
        String clientName = fieldClient.getText();
        PesquisarCliente pesquisarCliente = new PesquisarCliente(this, clientName);
        pesquisarCliente.start(new Stage());
    }

    private void fecthAndSeClient() {
        String clientName = fieldClient.getText();
        if(Validator.validateObject(clientName)) {
            ClientDAO dao = new ClientDAO();
            List<Client> clients = dao.selectClientByName(clientName);
            if(clients.isEmpty()) {
                AlertBox.unregisteredClient();
            }
            else {
                Client client = clients.get(FIRST);
                sale.setCliente(client);
                fieldClient.setText(client.getName());
                fieldProductCode.requestFocus();
            }
        }
    }

    private void setItemQuantity() {
        Integer quantityItem = retrieveItemQuantity();
        if (Validator.validateObject(quantityItem)) {
            if(Validator.validateQuantity(quantityItem)) {
                item.setQuantity(quantityItem);
                sale.modifyItemQuantity(item);
                fillTableItems();
                calculateValues();
            }
            else {
                AlertBox.invalidQuantityValue();
                retrieveItemQuantity();
            }
        }
    }

    private Integer retrieveItemQuantity() {
        String quantityItem = fieldQuantity.getText();
        if(!quantityItem.isBlank()) {
            if (Validator.validateInteger(quantityItem)) {
                return Integer.parseInt(quantityItem);
            } else {
                AlertBox.onlyNumbers();
                resetItemQuantity();
            }
        }
        return null;
    }

    private void resetItemQuantity() {
        fieldQuantity.setText(item.getQuantity().toString());
    }

    private void defineReceivedValue() {
        Double receivedValue = retrieveReceivedValue();
        if(Validator.validateObject(receivedValue)) {
            if(Validator.validateQuantity(receivedValue)) {
                calculateExchangeValue();
            }
            else {
                AlertBox.invalidValue();
                resetReceivedValue();
            }
        }
    }

    private Double retrieveReceivedValue() {
        String receivedValue = Helper.ignoreComma(fieldValueReceived.getText());
        if(receivedValue.isBlank()) {
            return null;
        }
        else if(Validator.validateDouble(receivedValue)) {
            return Double.parseDouble(receivedValue);
        }
        else {
            AlertBox.onlyNumbers();
            resetReceivedValue();
            return null;
        }
    }

    private void resetReceivedValue() {
        fieldValueReceived.setText(Helper.formatNumber(INITIAL_VALUE_DOUBLE));
        calculateExchangeValue();
    }

    private void changeItem() {
        Item item = tableItems.getSelectionModel().getSelectedItem();
        if(Validator.validateObject(item)) {
            this.item = item;
            showItem();
        }
    }

    private void removeItem() {
        Item item = tableItems.getSelectionModel().getSelectedItem();
        if(Validator.validateObject(item)) {
            sale.removeItemToList(item);
            resetItem();
        }
    }

    private void sell() {
        if(!sale.getItems().isEmpty()) {
            if(Validator.validateObject(sale.getCliente())) {
                if(validatePaymentAmount()) {
                    if(checkStock()) {
                        sale.setDate(Helper.getCurrentDate());
                        SaleDAO dao = new SaleDAO();
                        if (dao.insert(sale)) {
                            registerItemsSold();
                            finalizeSale();
                            AlertBox.sallerCompleted();
                        } else {
                            AlertBox.operationError();
                        }
                    }
                    else {
                        AlertBox.insufficientStock();
                    }
                }
                else {
                    AlertBox.insufficientValueReceived();
                }
            }
            else {
                AlertBox.insertAClient();
            }
        }
        else {
            AlertBox.insertAProduct();
        }
    }

    private boolean validatePaymentAmount() {
        Double receivedValue = retrieveReceivedValue();
        if(Validator.validateObject(receivedValue)) {
            Double total = sale.getValue();
            return receivedValue >= total;
        }
        return false;
    }

    private boolean checkStock() {
        boolean flag = true;
        for (Item item : sale.getItems()) {
            StockDAO dao = new StockDAO();
            List<Stock> stockList = dao.selectStockByCode(item.getProduto().getCode());
            Stock stock = stockList.isEmpty() ? null : stockList.get(0);
            if (stock == null) {
                flag = false;
                break;
            } else {
                if (stock.getQuantity() < item.getQuantity()) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private void registerItemsSold() {
        for (Item item : sale.getItems()) {
            ItemDAO dao = new ItemDAO();
            if (dao.insert(item)) {
                StockDAO stockDAO = new StockDAO();
                Long productCode = item.getProduto().getCode();
                Stock stock = stockDAO.selectStockByCode(productCode).get(0);
                stockDAO.withdrawStock(stock, item.getQuantity());
            }
        }
    }

    private void finalizeSale() {
        sale = new Sale(new ArrayList<Item>());
        sale.setOperator(Access.getOperator());
        sale.setDate(Helper.getCurrentDate());
        item = null;
        resetSale();
        fillTableItems();
    }

    private void resetSale() {
        textProductDescription.setText(DEFAULT_TEXT);
        textValueUnitary.setText(DEFAULT_TEXT);
        textValueTotal.setText(DEFAULT_TEXT);
        textValueBuy.setText(DEFAULT_TEXT);
        textNumberItems.setText(DEFAULT_TEXT);
        textExchange.setText(DEFAULT_TEXT);
        fieldQuantity.clear();
        fieldValueReceived.clear();
        fieldProductCode.clear();
        fieldProductDescription.clear();
        fieldClient.clear();
    }
}