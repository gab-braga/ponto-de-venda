package controller;

import controller.util.AlertBox;
import controller.util.SearchGuide;
import controller.util.Helper;
import controller.util.Validator;
import dao.*;
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
import model.*;
import view.PesquisarCliente;
import view.PesquisarProduto;

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

    private Venda venda;

    private Item item;

    public CaixaController() {
        venda = new Venda(new ArrayList<Item>());
        venda.setOperator(Access.getOperator());
        venda.setDataHora(Helper.getCurrentDate());
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
        if(o instanceof Produto) {
            Produto product = (Produto) o;
            setItem(new Item(product));
            venda.addItemToList(getItem());
            showItem();
        }
        else if(o instanceof Cliente) {
            Cliente client = (Cliente) o;
            venda.setCliente(client);
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
            textProductDescription.setText(item.getProduto().getDescricao());
            textValueUnitary.setText(Helper.formatNumber(item.getProduto().getValorVenda()));
            fieldQuantity.setText(item.getQuantidade().toString());
            fieldProductCode.setText(item.getProduto().getCodigo().toString());
            fieldProductDescription.setText(item.getProduto().getDescricao());
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
        tableColumnDescription.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescricao()));
        tableColumnQuantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        ObservableList<Item> items = FXCollections.observableArrayList(venda.getItems());
        tableItems.setItems(items);
        tableItems.refresh();
        textNumberItems.setText(Integer.toString(venda.getItems().size()));
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
        if(Validator.validateObject(venda)) {
            Double totalSale = INITIAL_VALUE_DOUBLE;
            for(Item item : venda.getItems()) {
                totalSale += item.getTotalValue();
            }
            venda.setValor(Helper.roundNumberTwoDecimalPlaces(totalSale));
            textValueBuy.setText(Helper.formatNumber(venda.getValor()));
        }
    }

    private void calculateExchangeValue() {
        Double receivedValue = retrieveReceivedValue();
        if(Validator.validateObject(receivedValue)) {
            Double totalValue = venda.getValor();
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
        fieldClient.setText(venda.getCliente().getNome());
    }

    private void fillInfoBar() {
        if(venda.getOperator() != null)
            textOperator.setText(venda.getOperator().getNome());
        if(venda.getDataHora() != null)
            textDate.setText(Helper.formatDate(venda.getDataHora()));
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
        Integer productCode = retrieveProductCode();
        if(Validator.validateObject(productCode)) {
            List<Produto> produtos = ProdutoDAO.queryProductByCode(productCode);
            if(produtos.isEmpty()) {
                AlertBox.unregisteredProduct();
            }
            else {
                Produto product = produtos.get(FIRST);
                item = new Item(product);
                venda.addItemToList(item);
                showItem();
                fieldQuantity.requestFocus();
            }
        }
    }

    private Integer retrieveProductCode() {
        String productCode = fieldProductCode.getText();
        if(Validator.validateInteger(productCode)) {
            return Integer.parseInt(productCode);
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
            List<Cliente> clientes = ClienteDAO.queryByNameClients(clientName);
            if(clientes.isEmpty()) {
                AlertBox.unregisteredClient();
            }
            else {
                Cliente client = clientes.get(FIRST);
                venda.setCliente(client);
                fieldClient.setText(client.getNome());
                fieldProductCode.requestFocus();
            }
        }
    }

    private void setItemQuantity() {
        Integer quantityItem = retrieveItemQuantity();
        if (Validator.validateObject(quantityItem)) {
            if(Validator.validateQuantity(quantityItem)) {
                item.setQuantidade(quantityItem);
                venda.modifyItemQuantity(item);
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
        fieldQuantity.setText(item.getQuantidade().toString());
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
            venda.removeItemToList(item);
            resetItem();
        }
    }

    private void sell() {
        if(!venda.getItems().isEmpty()) {
            if(Validator.validateObject(venda.getCliente())) {
                if(validatePaymentAmount()) {
                    if(checkStock()) {
                        venda.setDataHora(Helper.getCurrentDate());
                        Caixa caixa = new Caixa(venda.getValor(), 0.0, venda.getDataHora());
                        if (CaixaDAO.register(caixa)) {
                            venda.setCaixa(caixa);
                            if (VendaDAO.register(venda)) {
                                registerItemsSold();
                                AlertBox.sallerCompleted();
                                finalizeSale();
                            } else {
                                AlertBox.operationError();
                            }
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
            Double total = venda.getValor();
            return receivedValue >= total;
        }
        return false;
    }

    private boolean checkStock() {
        boolean flag = true;
        for (Item item : venda.getItems()) {
            Estoque estoque = EstoqueDAO.getStockByCode(item.getProduto().getCodigo());
            if (estoque == null) {
                flag = false;
                break;
            } else {
                if (estoque.getQuantidade() < item.getQuantidade()) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private void registerItemsSold() {
        for (Item item : venda.getItems()) {
            Estoque estoque = EstoqueDAO.getStockByCode(item.getProduto().getCodigo());
            estoque.setQuantidade(item.getQuantidade());
            if (EstoqueDAO.decrease(estoque)) {
                ItemDAO.register(item);
            }
        }
    }

    private void finalizeSale() {
        venda = new Venda(new ArrayList<>());
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