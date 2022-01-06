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
import java.util.Date;
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
    private Button btnSearchClient;

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
    private Text textChange;

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

    private final double INITIAL_VALUE_DOUBLE = 0.0;
    private final int INITIAL_VALUE_INTEGER = 0;
    private final int INITIAL_VALUE_QUANTITY = 1;

    private Date date;
    private Usuario operator;

    private double valueReceived = INITIAL_VALUE_DOUBLE;
    private int quantity = INITIAL_VALUE_QUANTITY;

    private double valueBuy = INITIAL_VALUE_DOUBLE;

    private Cliente cliente;
    private Produto produto;

    private List<Item> itemsBuy = new ArrayList<Item>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defineOperator();
        defineDate();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnAddItem.setOnMouseClicked(click -> {
            searchProduct();
        });

        btnSearchClient.setOnMouseClicked(click -> {
            searchClient();
        });

        fieldClient.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchClient();
        });

        fieldQuantity.setOnKeyReleased(keyEvent -> {
            modifiedQuantityValue();
        });

        fieldValueReceived.setOnKeyReleased(keyEvent -> {
            modifiedReceivedValue();
        });

        fieldProductDescription.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchProduct();
        });

        fieldProductCode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                defineProduct();
        });

        tableItems.setOnMouseClicked(click -> {
            viewItem();
        });

        tableItemRemove.setOnAction(action -> {
            removeAndResetProduct();
        });

        tableItems.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE)
                removeAndResetProduct();
        });

        btnSubmit.setOnMouseClicked(click -> {
            defineDate();
            seller();
        });

        Helper.addTextLimiter(fieldQuantity, 3);
        Helper.addTextLimiter(fieldValueReceived, 9);
        Helper.addTextLimiter(fieldClient, 50);
        Helper.addTextLimiter(fieldProductDescription, 80);
        Helper.addTextLimiter(fieldProductCode, 20);
    }

    @Override
    public void searchAndFillData(Object o) {
        if(o instanceof Produto) {
            addNewProduct(((Produto) o));
        }
        else if(o instanceof Cliente) {
            addNewClient(((Cliente) o));
        }
    }

    private void addNewProduct(Produto produto) {
        if (Validator.validateObject(produto)) {
            setProduto(produto);
            insertAndFillQuantityProduto(getProduto());
            addItemAndUpdateTable(getProduto(), getQuantity());
            fillInUnitValueField(getProduto());
            calculateAndFillValueTotal(getQuantity(), getProduto());
            calculateAndFillValueBuy(getItemsBuy());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            textProductDescription.setText(getProduto().getDescricao());
            fieldProductCode.setText(Integer.toString(getProduto().getCodigo()));
            fieldProductDescription.setText(getProduto().getDescricao());
        }
    }

    private void addNewClient(Cliente cliente) {
        if (Validator.validateObject(cliente)) {
            setCliente(cliente);
            fieldClient.setText(getCliente().getNome());
        }
    }

    private void defineOperator() {
        Usuario usuario = Access.getOperator();
        if(usuario != null) {
            setOperator(usuario);
            textOperator.setText(getOperator().getNome());
        }
    }

    private void defineDate() {
        Date date = Helper.getCurrentDate();
        if(date != null) {
            setDate(date);
            textDate.setText(Helper.formatDate(getDate()));
        }
    }

    private void defineProduct() {
        String codeProduct = fieldProductCode.getText();
        if (codeProduct.isBlank()) {
            searchProduct();
        } else {
            if (Validator.validateInteger(codeProduct)) {
                List<Produto> produtos = ProdutoDAO.queryProductByCode(Integer.parseInt(codeProduct));
                if (produtos.size() > 0) {
                    searchAndFillData(produtos.get(0));
                } else {
                    AlertBox.unregisteredProduct();
                }
            } else {
                AlertBox.onlyNumbers();
            }
        }
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    private void insertAndFillQuantityProduto(Produto produto) {
        boolean isExist = false;
        List<Item> items = getItemsBuy();
        for (Item item : items) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                int quantityValue = item.getQuantidade() + INITIAL_VALUE_QUANTITY;
                setQuantity(quantityValue);
                item.setQuantidade(quantityValue);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            setQuantity(INITIAL_VALUE_QUANTITY);
        }
        fieldQuantity.setText(Integer.toString(getQuantity()));
    }

    private void insertAndFillQuantityProduto(Produto produto, int quantity) {
        setQuantity(quantity);
        List<Item> items = getItemsBuy();
        for (Item item : items) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                item.setQuantidade(quantity);
                break;
            }
        }
    }

    private void fillInUnitValueField(Produto produto) {
        double valueUnitary = produto.getValorVenda();
        if (Validator.validateObject(getProduto())) {
            textValueUnitary.setText(Helper.parseNumberToText(valueUnitary));
        } else {
            this.attentionInsertAProduct();
        }
    }

    private void searchClient() {
        String clientName = fieldClient.getText();
        PesquisarCliente pesquisarCliente = new PesquisarCliente(this, clientName);
        pesquisarCliente.start(new Stage());
    }

    private void searchProduct() {
        String productDescription = fieldProductDescription.getText();
        PesquisarProduto pesquisarProduto = new PesquisarProduto(this, productDescription);
        pesquisarProduto.start(new Stage());
    }

    private void attentionInsertAProduct() {
        AlertBox.insertAProduct();
        clearFields();
        fieldProductCode.requestFocus();
    }

    private void attentionInsertAClient() {
        AlertBox.insertAClient();
        fieldClient.requestFocus();
    }

    private void returnQuantityValueInField() {
        fieldQuantity.setText(Integer.toString(getQuantity()));
        modifiedQuantityValue();
    }

    private void returnReceivedValueInField() {
        fieldValueReceived.setText(Helper.parseNumberToText(getValueReceived()));
        modifiedReceivedValue();
    }

    private void clearFields() {
        textProductDescription.setText("-");
        textValueUnitary.setText("-");
        fieldQuantity.clear();
        textValueTotal.setText("-");
        fieldProductCode.clear();
        fieldProductDescription.clear();
    }

    private int getValueOfTheQuantityEnteredInTheField() {
        int quantityValue = getQuantity();
        String quantityString = fieldQuantity.getText();
        if (Validator.validateInteger(quantityString)) {
            int quantityInteger = Integer.parseInt(quantityString);
            if (Validator.validateQuantity(quantityInteger)) {
                quantityValue = quantityInteger;
            } else {
                AlertBox.invalidQuantityValue();
                returnQuantityValueInField();
            }
        } else if (quantityString.isBlank()) {
            quantityValue = INITIAL_VALUE_INTEGER;
        } else {
            AlertBox.onlyNumbers();
            returnQuantityValueInField();
        }
        return quantityValue;
    }

    private double getReceivedValueEnteredInTheField() {
        double receivedValue = getValueReceived();
        String receivedString = fieldValueReceived.getText().replace(",", ".");
        if (Validator.validateDouble(receivedString)) {
            double receivedDouble = Double.parseDouble(receivedString);
            if (Validator.validateQuantity(receivedDouble)) {
                receivedValue = receivedDouble;
            } else {
                AlertBox.invalidQuantityValue();
                returnQuantityValueInField();
            }
        } else if (receivedString.isBlank()) {
            receivedValue = INITIAL_VALUE_DOUBLE;
        } else {
            AlertBox.invalidQuantityValue();
            returnReceivedValueInField();
        }
        return receivedValue;
    }

    public void removeAndResetProduct() {
        Item item = tableItems.getSelectionModel().getSelectedItem();
        if (!(item == null)) {
            Produto produto = item.getProduto();
            removeItem(produto);
            if (produto.getCodigo() == getProduto().getCodigo()) {
                setProduto(null);
                setQuantity(INITIAL_VALUE_QUANTITY);
                clearFields();
                calculateAndFillValueTotal(getQuantity(), getProduto());

            }
            calculateAndFillValueBuy(getItemsBuy());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            fillTableItems(getItemsBuy());
        }
    }

    private void viewItem() {
        Item item = tableItems.getSelectionModel().getSelectedItem();
        if (!(item == null)) {
            Produto produto = item.getProduto();
            setProduto(produto);
            setQuantity(item.getQuantidade());
            fillInUnitValueField(getProduto());
            calculateAndFillValueTotal(getQuantity(), getProduto());
            calculateAndFillValueBuy(getItemsBuy());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            fieldQuantity.setText(Integer.toString(getQuantity()));
            textProductDescription.setText(getProduto().getDescricao());
            fieldProductCode.setText(Integer.toString(getProduto().getCodigo()));
            fieldProductDescription.setText(getProduto().getDescricao());
        }
    }

    private void removeItem(Produto produto) {
        if (Validator.validateObject(getProduto())) {
            List<Item> items = getItemsBuy();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getProduto().getCodigo() == produto.getCodigo()) {
                    items.remove(i);
                    break;
                }
            }
        }
    }

    private void addItemAndUpdateTable(Produto produto, int quantity) {
        boolean isExist = false;
        List<Item> items = getItemsBuy();
        for (Item item : items) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            items.add(new Item(produto, quantity));
        }
        fillTableItems(getItemsBuy());
    }

    private void fillTableItems(List<Item> itemsList) {
        tableColumnDescription.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescricao()));
        tableColumnQuantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        ObservableList<Item> items = FXCollections.observableArrayList(itemsList);
        tableItems.setItems(items);
        tableItems.refresh();

        alterNumberItems(itemsList);
    }

    private void alterNumberItems(List<Item> items) {
        int numberItems = items.size();
        if (numberItems == 0) {
            textNumberItems.setText("-");
        } else {
            textNumberItems.setText(Integer.toString(numberItems));
        }
    }

    private void calculateAndFillValueTotal(int quantity, Produto produto) {
        if (Validator.validateObject(getProduto())) {
            double valueTotal = Helper.roundNumberTwoDecimalPlaces(produto.getValorVenda() * quantity);
            textValueTotal.setText(Helper.parseNumberToText(valueTotal));
        } else {
            textValueTotal.setText("-");
        }
    }

    private void calculateAndFillValueBuy(List<Item> items) {
        if (items.isEmpty()) {
            textValueBuy.setText("-");
            setValueBuy(INITIAL_VALUE_DOUBLE);
        } else {
            double valueBuy = INITIAL_VALUE_DOUBLE;
            for (Item item : items) {
                valueBuy += (item.getProduto().getValorVenda() * item.getQuantidade());
            }
            setValueBuy(Helper.roundNumberTwoDecimalPlaces(valueBuy));
            textValueBuy.setText(Helper.parseNumberToText(getValueBuy()));
        }
    }

    private void calculateAndFillChange(double valueReceived, double valueBuy) {
        if ((valueReceived == INITIAL_VALUE_DOUBLE) || (valueBuy == INITIAL_VALUE_DOUBLE)) {
            textChange.setText("-");
        } else {
            if (valueBuy <= valueReceived) {
                double valueChange = Helper.roundNumberTwoDecimalPlaces(valueReceived - valueBuy);
                textChange.setText(Helper.parseNumberToText(valueChange));
            } else {
                textChange.setText(Helper.parseNumberToText(INITIAL_VALUE_DOUBLE));
            }
        }
    }

    private void modifiedQuantityValue() {
        if (Validator.validateObject(getProduto())) {
            int quantityValue = getValueOfTheQuantityEnteredInTheField();
            insertAndFillQuantityProduto(getProduto(), quantityValue);
            calculateAndFillValueTotal(getQuantity(), getProduto());
            calculateAndFillValueBuy(getItemsBuy());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            fillTableItems(getItemsBuy());
        } else {
            attentionInsertAProduct();
        }
    }

    private void modifiedReceivedValue() {
        double receivedValue = getReceivedValueEnteredInTheField();
        setValueReceived(Helper.roundNumberTwoDecimalPlaces(receivedValue));
        calculateAndFillChange(getValueReceived(), getValueBuy());
    }

    private void finalizeSale() {
        setCliente(null);
        setProduto(null);
        setQuantity(INITIAL_VALUE_QUANTITY);
        setItemsBuy(new ArrayList<Item>());
        clearFields();
        fieldValueReceived.clear();
        fieldClient.clear();
        calculateAndFillValueTotal(getQuantity(), getProduto());
        calculateAndFillValueBuy(getItemsBuy());
        calculateAndFillChange(getValueReceived(), getValueBuy());
        fillTableItems(getItemsBuy());
    }

    private void registerItemsSold(List<Item> items, Venda venda) {
        for (Item item : items) {
            Estoque estoque = EstoqueDAO.getStockByCode(item.getProduto().getCodigo());
            estoque.setQuantidade(item.getQuantidade());
            if (EstoqueDAO.decrease(estoque)) {
                item.setVenda(venda);
                ItemDAO.register(item);
            }
        }
    }

    private boolean checkStock(List<Item> items) {
        boolean flag = true;
        for (Item item : items) {
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

    private boolean validatePayment() {
        boolean flag = false;
        if (getValueReceived() >= getValueBuy()) {
            flag = true;
        }
        return flag;
    }

    private void seller() {
        if (getItemsBuy().size() > 0) {
            if (Validator.validateObject(getCliente())) {
                if (validatePayment()) {
                    List<Item> items = getItemsBuy();
                    if (checkStock(items)) {
                        Caixa caixa = new Caixa(getValueBuy(), 0.0, getDate());
                        if (CaixaDAO.register(caixa)) {
                            Venda venda = new Venda(getValueBuy(), getDate(), getCliente(), caixa, getOperator());
                            if (VendaDAO.register(venda)) {
                                registerItemsSold(items, venda);
                                AlertBox.sallerCompleted();
                                finalizeSale();
                            } else {
                                AlertBox.operationError();
                            }
                        } else {
                            AlertBox.operationError();
                        }
                    } else {
                        AlertBox.insufficientStock();
                    }
                } else {
                    AlertBox.insufficientValueReceived();
                }
            } else {
                attentionInsertAClient();
            }
        } else {
            attentionInsertAProduct();
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Usuario getOperator() {
        return operator;
    }

    public void setOperator(Usuario operator) {
        this.operator = operator;
    }

    public double getValueReceived() {
        return valueReceived;
    }

    public void setValueReceived(double valueReceived) {
        this.valueReceived = valueReceived;
    }

    public int getQuantity() {
        return quantity;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getValueBuy() {
        return valueBuy;
    }

    public void setValueBuy(double valueBuy) {
        this.valueBuy = valueBuy;
    }

    public List<Item> getItemsBuy() {
        return itemsBuy;
    }

    public void setItemsBuy(List<Item> itemsBuy) {
        this.itemsBuy = itemsBuy;
    }
}