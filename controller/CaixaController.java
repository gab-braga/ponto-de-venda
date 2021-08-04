package controller;

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

public class CaixaController implements Initializable, DataDriver {

    @FXML
    private AnchorPane root;

    @FXML
    private Text text_product_description;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_seller;

    @FXML
    private Text text_value_unitary;

    @FXML
    private TextField field_quantity;

    @FXML
    private TextField field_value_received;

    @FXML
    private Text text_change;

    @FXML
    private Text text_value_buy;

    @FXML
    private Text text_value_total;

    @FXML
    private TextField field_client;

    @FXML
    private Button btn_search_client;

    @FXML
    private Button btn_add_item;

    @FXML
    private Text text_number_items;

    @FXML
    private TableView<Item> table_items;

    @FXML
    private TableColumn<Item, String> table_column_description;

    @FXML
    private TableColumn<Item, Integer> table_column_quantity;

    @FXML
    private MenuItem table_item_remove;

    @FXML
    private TextField field_product_code;

    @FXML
    private TextField field_product_description;

    @FXML
    private Text text_operator;

    @FXML
    private Text text_date;

    private final Double initialValueDouble = 0.0;
    private final int initialValueInteger = 0;
    private final int initialValueQuantity = 1;

    private Date date;
    private Usuario operator;

    private double valueReceived = initialValueDouble;
    private int quantity = initialValueQuantity;

    private double valueBuy = initialValueDouble;

    private Cliente cliente;
    private Produto produto;

    private List<Item> items = new ArrayList<Item>();

    // GETTERS AND SETTERS
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    // PUBLIC FUNCTIONS
    @Override
    public void insertAndFillClient(Cliente cliente) {
        if (Helper.validateClient(cliente)) {
            setCliente(cliente);
            field_client.setText(getCliente().getNome());
        }
    }

    @Override
    public void insertAndFillProduct(Produto produto) {
        if (Helper.validateProduct(produto)) {
            setProduto(produto);
            insertAndFillQuantityProduto(getProduto());
            addItemAndUpdateTable(getProduto(), getQuantity());
            fillInUnitValueField(getProduto());
            calculateAndFillValueTotal(getQuantity(), getProduto());
            calculateAndFillValueBuy(getItems());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            text_product_description.setText(getProduto().getDescricao());
            field_product_code.setText(Integer.toString(getProduto().getCodigo()));
            field_product_description.setText(getProduto().getDescricao());
        }
    }

    private void insertAndFillQuantityProduto(Produto produto) {
        boolean isExist = false;
        List<Item> items = getItems();
        for (Item item : items) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                int quantityValue = item.getQuantidade() + initialValueQuantity;
                setQuantity(quantityValue);
                item.setQuantidade(quantityValue);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            setQuantity(initialValueQuantity);
        }
        field_quantity.setText(Integer.toString(getQuantity()));
    }

    private void insertAndFillQuantityProduto(Produto produto, int quantity) {
        setQuantity(quantity);
        List<Item> items = getItems();
        for (Item item : items) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                item.setQuantidade(quantity);
                break;
            }
        }
    }

    // PRIVATE FUNCTIONS
    private void fillInUnitValueField(Produto produto) {
        double valueUnitary = produto.getValorVenda();
        if (Helper.validateProduct(getProduto())) {
            text_value_unitary.setText(Helper.getStringValueDouble(valueUnitary));
        } else {
            this.attentionInsertAProduct();
        }
    }

    private void defineOperator() {
        setOperator(Access.getUser());
        text_operator.setText(getOperator().getNome());
    }

    private void defineDate() {
        setDate(Helper.getCurrentDate());
        text_date.setText(Helper.getStringDateFormatted(getDate()));
    }

    private void defineProduct() {
        String code_product = field_product_code.getText();
        if (code_product.isEmpty()) {
            searchProduct();
        } else {
            if (Helper.validateInteger(code_product)) {
                List<Produto> produtos = ProdutoDAO.queryProductByCode(Integer.parseInt(code_product));
                if (produtos.size() > 0) {
                    insertAndFillProduct(produtos.get(0));
                } else {
                    AlertBox.unregisteredProduct();
                }
            } else {
                AlertBox.onlyNumbers();
            }
        }
    }

    private void searchClient() {
        String clientName = field_client.getText();
        PesquisarCliente pesquisarCliente = new PesquisarCliente(this, clientName);
        pesquisarCliente.start(new Stage());
    }

    private void searchProduct() {
        String productDescription = field_product_description.getText();
        PesquisarProduto pesquisarProduto = new PesquisarProduto(this, productDescription);
        pesquisarProduto.start(new Stage());
    }

    private void attentionInsertAProduct() {
        AlertBox.insertAProduct();
        clearFields();
        field_product_code.requestFocus();
    }

    private void attentionInsertAClient() {
        AlertBox.insertAClient();
        field_client.requestFocus();
    }

    private void returnQuantityValueInField() {
        field_quantity.setText(Integer.toString(getQuantity()));
        modifiedQuantityValue();
    }

    private void returnReceivedValueInField() {
        field_value_received.setText(Helper.getStringValueDouble(getValueReceived()));
        modifiedReceivedValue();
    }

    private void clearFields() {
        text_product_description.setText("-");
        text_value_unitary.setText("-");
        field_quantity.clear();
        text_value_total.setText("-");
        field_product_code.clear();
        field_product_description.clear();
    }

    private int getValueOfTheQuantityEnteredInTheField() {
        int quantityValue = getQuantity();
        String quantityString = field_quantity.getText();
        if (Helper.validateInteger(quantityString)) {
            int quantityInteger = Integer.parseInt(quantityString);
            if (Helper.validateQuantity(quantityInteger)) {
                quantityValue = quantityInteger;
            } else {
                AlertBox.invalidQuantityValue();
                returnQuantityValueInField();
            }
        } else if (quantityString.isEmpty()) {
            quantityValue = initialValueInteger;
        } else {
            AlertBox.onlyNumbers();
            returnQuantityValueInField();
        }
        return quantityValue;
    }

    private double getReceivedValueEnteredInTheField() {
        double receivedValue = getValueReceived();
        String receivedString = field_value_received.getText().replace(",", ".");
        if (Helper.validateDouble(receivedString)) {
            double receivedDouble = Double.parseDouble(receivedString);
            if (Helper.validateQuantity(receivedDouble)) {
                receivedValue = receivedDouble;
            } else {
                AlertBox.invalidQuantityValue();
                returnQuantityValueInField();
            }
        } else if (receivedString.isEmpty()) {
            receivedValue = initialValueDouble;
        } else {
            AlertBox.invalidQuantityValue();
            returnReceivedValueInField();
        }
        return receivedValue;
    }

    public void removeAndResetProduct() {
        Item item = table_items.getSelectionModel().getSelectedItem();
        if (!(item == null)) {
            Produto produto = item.getProduto();
            removeItem(produto);
            if (produto.getCodigo() == getProduto().getCodigo()) {
                setProduto(null);
                setQuantity(initialValueQuantity);
                clearFields();
                calculateAndFillValueTotal(getQuantity(), getProduto());

            }
            calculateAndFillValueBuy(getItems());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            fillTableItems(getItems());
        }
    }

    private void viewItem() {
        Item item = table_items.getSelectionModel().getSelectedItem();
        if (!(item == null)) {
            Produto produto = item.getProduto();
            setProduto(produto);
            setQuantity(item.getQuantidade());
            fillInUnitValueField(getProduto());
            calculateAndFillValueTotal(getQuantity(), getProduto());
            calculateAndFillValueBuy(getItems());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            field_quantity.setText(Integer.toString(getQuantity()));
            text_product_description.setText(getProduto().getDescricao());
            field_product_code.setText(Integer.toString(getProduto().getCodigo()));
            field_product_description.setText(getProduto().getDescricao());
        }
    }

    private void removeItem(Produto produto) {
        if (Helper.validateProduct(getProduto())) {
            List<Item> items = getItems();
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
        List<Item> items = getItems();
        for (Item item : items) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            items.add(new Item(produto, quantity));
        }
        fillTableItems(getItems());
    }

    private void fillTableItems(List<Item> itemsList) {
        table_column_description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescricao()));
        table_column_quantity.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        ObservableList<Item> items = FXCollections.observableArrayList(itemsList);
        table_items.setItems(items);
        table_items.refresh();

        alterNumberItems(itemsList);
    }

    private void alterNumberItems(List<Item> items) {
        int numberItems = items.size();
        if (numberItems == 0) {
            text_number_items.setText("-");
        } else {
            text_number_items.setText(Integer.toString(numberItems));
        }
    }

    private void calculateAndFillValueTotal(int quantity, Produto produto) {
        if (Helper.validateProduct(getProduto())) {
            double valueTotal = Helper.round(produto.getValorVenda() * quantity);
            text_value_total.setText(Helper.getStringValueDouble(valueTotal));
        } else {
            text_value_total.setText("-");
        }
    }

    private void calculateAndFillValueBuy(List<Item> items) {
        if (items.isEmpty()) {
            text_value_buy.setText("-");
            setValueBuy(initialValueDouble);
        } else {
            double valueBuy = initialValueDouble;
            for (Item item : items) {
                valueBuy += (item.getProduto().getValorVenda() * item.getQuantidade());
            }
            setValueBuy(Helper.round(valueBuy));
            text_value_buy.setText(Helper.getStringValueDouble(getValueBuy()));
        }
    }

    private void calculateAndFillChange(double valueReceived, double valueBuy) {
        if ((valueReceived == initialValueDouble) || (valueBuy == initialValueDouble)) {
            text_change.setText("-");
        } else {
            if (valueBuy <= valueReceived) {
                double valueChange = Helper.round(valueReceived - valueBuy);
                text_change.setText(Helper.getStringValueDouble(valueChange));
            } else {
                text_change.setText(Helper.getStringValueDouble(initialValueDouble));
            }
        }
    }

    private void modifiedQuantityValue() {
        if (Helper.validateProduct(getProduto())) {
            int quantityValue = getValueOfTheQuantityEnteredInTheField();
            insertAndFillQuantityProduto(getProduto(), quantityValue);
            calculateAndFillValueTotal(getQuantity(), getProduto());
            calculateAndFillValueBuy(getItems());
            calculateAndFillChange(getValueReceived(), getValueBuy());
            fillTableItems(getItems());
        } else {
            attentionInsertAProduct();
        }
    }

    private void modifiedReceivedValue() {
        double receivedValue = getReceivedValueEnteredInTheField();
        setValueReceived(Helper.round(receivedValue));
        calculateAndFillChange(getValueReceived(), getValueBuy());
    }

    private void finalizeSale() {
        setCliente(null);
        setProduto(null);
        setQuantity(initialValueQuantity);
        setItems(new ArrayList<Item>());
        clearFields();
        field_value_received.clear();
        field_client.clear();
        calculateAndFillValueTotal(getQuantity(), getProduto());
        calculateAndFillValueBuy(getItems());
        calculateAndFillChange(getValueReceived(), getValueBuy());
        fillTableItems(getItems());
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
        if (getItems().size() > 0) {
            if (Helper.validateClient(getCliente())) {
                if (validatePayment()) {
                    List<Item> items = getItems();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defineOperator();
        defineDate();

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_add_item.setOnMouseClicked(click -> {
            searchProduct();
        });

        btn_search_client.setOnMouseClicked(click -> {
            searchClient();
        });

        field_client.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchClient();
        });

        field_quantity.setOnKeyReleased(keyEvent -> {
            modifiedQuantityValue();
        });

        field_value_received.setOnKeyReleased(keyEvent -> {
            modifiedReceivedValue();
        });

        field_product_description.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                searchProduct();
        });

        field_product_code.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                defineProduct();
        });

        table_items.setOnMouseClicked(click -> {
            viewItem();
        });

        table_item_remove.setOnAction(action -> {
            removeAndResetProduct();
        });

        table_items.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DELETE)
                removeAndResetProduct();
        });

        btn_seller.setOnMouseClicked(click -> {
            defineDate();
            seller();
        });

        Helper.addTextLimiter(field_quantity, 3);
        Helper.addTextLimiter(field_value_received, 9);

        Helper.addTextLimiter(field_client, 50);
        Helper.addTextLimiter(field_product_description, 80);
        Helper.addTextLimiter(field_product_code, 20);
    }
}