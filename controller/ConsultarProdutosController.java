package controller;

import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Produto;
import view.ConsultarProdutos;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarProdutosController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_code;

    @FXML
    private TextField field_search_description;

    @FXML
    private Button btn_close;

    @FXML
    private TableView<Produto> table_product;

    @FXML
    private TableColumn<Produto, Integer> column_code;

    @FXML
    private TableColumn<Produto, String> column_description;

    @FXML
    private TableColumn<Produto, Double> column_sale_value;

    private void fillTable(List<Produto> produtos) {
        column_code.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("codigo"));
        column_description.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
        column_sale_value.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));

        ObservableList<Produto> items = FXCollections.observableArrayList(produtos);
        table_product.setItems(items);
        table_product.refresh();
    }

    private void filter() {
        String code = field_search_code.getText();
        String description = field_search_description.getText();
        boolean filterByCode = !code.isEmpty();
        boolean filterByDescription = !description.isEmpty();
        if(Helper.validateInteger(code) || !filterByCode) {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            if(!filterByCode && filterByDescription) {
                fillTable(produtoDAO.queryByDescriptionProducts(description));
            }
            else if(filterByCode && !filterByDescription) {
                fillTable(produtoDAO.queryByCodeProducts(Integer.parseInt(code)));
            }
            else if(filterByCode && filterByDescription) {
                fillTable(produtoDAO.queryByCodeOrDescriptionProducts(Integer.parseInt(code), description));
            }
            else {
                fillTable(produtoDAO.queryAllProducts());
            }
        }
    }

    private void close() {
        ConsultarProdutos.getWindow().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filter();

        field_search_code.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_description.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        field_search_code.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_search_code.getText().length() >= maxCharacters) event.consume();
        });

        field_search_description.setOnKeyTyped(event -> {
            int maxCharacters = 80;
            if(field_search_description.getText().length() >= maxCharacters) event.consume();
        });
    }
}