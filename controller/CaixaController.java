package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Cliente;
import model.Produto;
import view.Caixa;
import view.PesquisarCliente;
import view.PesquisarProduto;

import java.net.URL;
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
    private TextField field_value_unitary;

    @FXML
    private TextField field_quantity;

    @FXML
    private TextField field_value_total;

    @FXML
    private TextField field_value_received;

    @FXML
    private TextField field_change;

    @FXML
    private TextField field_client;

    @FXML
    private Button btn_search_client;

    @FXML
    private Text text_number_items;

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

    private Cliente cliente;
    private Produto produto;

    protected void fillFieldClient(Cliente cliente) {
        this.cliente = cliente;
        field_client.setText(cliente.getNome());
    }

    public void fillFieldProduct(Produto produto) {
        this.produto = produto;
        field_product_code.setText(Integer.toString(produto.getCodigo()));
        field_product_description.setText(produto.getDescricao());
        text_product_description.setText(produto.getDescricao());
    }

    private void defineOperator() {
        text_operator.setText(Acesso.getUser());
    }

    private void defineDate() {
        text_date.setText(Helper.getDateFormattedString(Helper.getCurrentDate()));
    }

    private void close() {
        Caixa.getWindow().close();
    }

    private void searchClient() {
        PesquisarCliente pesquisarCliente = new PesquisarCliente(this);
        pesquisarCliente.start(new Stage());
    }

    private void searchProduct() {
        PesquisarProduto pesquisarProduto = new PesquisarProduto(this);
        pesquisarProduto.start(new Stage());
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
    }
}