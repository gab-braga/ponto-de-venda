package controller;

import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Cliente;
import model.Produto;
import view.PesquisarCliente;
import view.PesquisarProduto;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CaixaController implements Initializable {

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
    private TextField field_client;

    @FXML
    private Button btn_search_client;

    @FXML
    private TextField field_product_code;

    @FXML
    private TextField field_product_description;

    @FXML
    private Button btn_search_product;

    @FXML
    private Text text_operator;

    @FXML
    private Text text_date;

    private final Double initialValueDouble = 0.0;
    private final int initialValueInteger = 0;
    private final int initialValueQuantity = 1;

    private Date date = Helper.getCurrentDate();
    private String operator = Acesso.getUser();

    private double valueReceived = initialValueDouble;
    private double valueBuy = initialValueDouble;
    private int quantity = initialValueQuantity;

    private Cliente cliente;
    private Produto produto;

    // GETTERS AND SETTERS
    public double getValueReceived() {
        return valueReceived;
    }

    public void setValueReceived(double valueReceived) {
        this.valueReceived = valueReceived;
    }

    public double getValueBuy() {
        return valueBuy;
    }

    public void setValueBuy(double valueBuy) {
        this.valueBuy = valueBuy;
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


    private void defineOperator() {
        text_operator.setText(this.operator);
    }

    private void defineDate() {
        text_date.setText(Helper.getDateFormattedString(this.date));
    }

    private void defineProduct() {
        String code_product = field_product_code.getText();
        if(code_product.isEmpty()) {
            searchProduct();
        }
        else {
            if(Helper.validateInteger(code_product)) {
                List<Produto> produtos = ProdutoDAO.queryByCodeProducts(Integer.parseInt(code_product));
                if(produtos.size() > 0) {
                    insertAndFillProduct(produtos.get(0));
                }
                else {
                    AlertBox.unregisteredProduct();
                }
            }
            else {
                AlertBox.onlyNumbers();
            }
        }
    }

    private void clearFields() {
        field_quantity.clear();
        field_value_received.clear();
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private void insertAProduct() {
        AlertBox.insertAProduct();
        clearFields();
        field_product_code.requestFocus();
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

    protected void insertAndFillClient(Cliente cliente) {
        if(!(cliente == null)) {
            setCliente(cliente);
            field_client.setText(getCliente().getNome());
        }
    }

    protected void insertAndFillProduct(Produto produto) {
        if(!(produto == null)) {
            setProduto(produto);
            field_product_code.setText(Integer.toString(getProduto().getCodigo()));
            field_product_description.setText(getProduto().getDescricao());
            text_product_description.setText(getProduto().getDescricao());
            fillValueUnitary(getProduto().getValorVenda());
        }
    }

    private boolean validateClient() {
        return !(this.cliente == null);
    }

    private boolean validateProduct() {
        return !(this.produto == null);
    }

    private void resetQuantityFieldValue() {
        field_quantity.setText(Integer.toString(getQuantity()));
        modifyQuantityValue();
    }

    private void resetValueReceivedFieldValue() {
        field_value_received.setText(Helper.getStringValueDouble(getValueReceived()));
        modifyReceivedValue();
    }

    private int getValueOfTheQuantityEnteredInTheField() {
        int quantityValue = getQuantity();
        String quantityString = field_quantity.getText();
        if(Helper.validateInteger(quantityString)) {
            int quantityInteger = Integer.parseInt(quantityString);
            if(Helper.validateQuantity(quantityInteger)) {
                quantityValue = quantityInteger;
            }
            else {
                AlertBox.invalidQuantityValue();
                resetQuantityFieldValue();
            }
        }
        else if(quantityString.isEmpty()) {
            quantityValue = initialValueInteger;
        }
        else {
            AlertBox.onlyNumbers();
            resetQuantityFieldValue();
        }
        return quantityValue;
    }

    private double getReceivedValueEnteredInTheField() {
        double receivedValue = getValueReceived();
        String receivedString = field_value_received.getText().replace(",", ".");
        if(Helper.validateDouble(receivedString)) {
            double receivedDouble = Double.parseDouble(receivedString);
            if(Helper.validateQuantity(receivedDouble)) {
                receivedValue = receivedDouble;
            }
            else {
                AlertBox.invalidQuantityValue();
                resetQuantityFieldValue();
            }
        }
        else if(receivedString.isEmpty()) {
            receivedValue = initialValueDouble;
        }
        else {
            AlertBox.invalidQuantityValue();
            resetValueReceivedFieldValue();
        }
        return receivedValue;
    }

    private void modifyQuantityValue() {
        if(validateProduct()) {
            int quantityValue = getValueOfTheQuantityEnteredInTheField();
            setQuantity(quantityValue);
            calculateAndFillValueBuy(getQuantity(), getProduto());
            calculateAndFillChange(getValueReceived(), getValueBuy());
        }
        else {
            this.insertAProduct();
        }
    }

    private void modifyReceivedValue() {
        if(validateProduct()) {
            double receivedValue = getReceivedValueEnteredInTheField();
            setValueReceived(receivedValue);
            calculateAndFillChange(getValueReceived(), getValueBuy());
        }
        else {
            this.insertAProduct();
        }
    }

    private void fillValueUnitary(double valueUnitary) {
        if(validateProduct()) {
            text_value_unitary.setText(Helper.getStringValueDouble(valueUnitary));
            resetQuantityFieldValue();
            calculateAndFillValueBuy(getQuantity(), getProduto());
            calculateAndFillChange(getValueReceived(), getValueBuy());
        }
        else {
            this.insertAProduct();
        }
    }

    private void calculateAndFillValueBuy(int quantityValue, Produto produto) {
        double valueBuy = Helper.round(produto.getValorVenda() * this.quantity);
        setValueBuy(valueBuy);
        text_value_buy.setText(Helper.getStringValueDouble(getValueBuy()));
    }

    private void calculateAndFillChange(double valueReceived, double valueBuy) {
        if(valueBuy <= valueReceived) {
            double valueChange = Helper.round(valueReceived - valueBuy);
            text_change.setText(Helper.getStringValueDouble(valueChange));
        }
        else {
            text_change.setText(Helper.getStringValueDouble(initialValueDouble));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defineOperator();
        defineDate();

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_search_client.setOnMouseClicked(click -> {
            searchClient();
        });

        btn_search_product.setOnMouseClicked(click -> {
            searchProduct();
        });

        field_quantity.setOnKeyReleased(keyEvent -> {
            modifyQuantityValue();
        });

        field_value_received.setOnKeyReleased(keyEvent -> {
            modifyReceivedValue();
        });

        field_client.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                searchClient();
        });

        field_product_description.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                searchProduct();
        });

        field_product_code.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                defineProduct();
        });

        Helper.addTextLimiter(field_quantity, 3);
        Helper.addTextLimiter(field_value_received, 9);

        Helper.addTextLimiter(field_client, 50);
        Helper.addTextLimiter(field_product_description, 80);
        Helper.addTextLimiter(field_product_code, 20);
    }
}