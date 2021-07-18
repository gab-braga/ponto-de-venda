package controller;

import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import dao.ClienteDAO;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cliente;
import model.Produto;
import view.ConsultarProdutos;
import view.EditarCliente;
import view.EditarProduto;

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

    @FXML
    private MenuItem table_item_refresh;

    @FXML
    private MenuItem table_item_edit;

    @FXML
    private MenuItem table_item_delete;

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
            if(!filterByCode && filterByDescription) {
                fillTable(ProdutoDAO.queryByDescriptionProducts(description));
            }
            else if(filterByCode && !filterByDescription) {
                fillTable(ProdutoDAO.queryByCodeProducts(Integer.parseInt(code)));
            }
            else if(filterByCode && filterByDescription) {
                fillTable(ProdutoDAO.queryByCodeOrDescriptionProducts(Integer.parseInt(code), description));
            }
            else {
                fillTable(ProdutoDAO.queryAllProducts());
            }
        }
    }

    private void edit() {
        Produto produto = table_product.getSelectionModel().getSelectedItem();
        if(produto == null) {
            AlertBox.selectARecord();
        }
        else {
            EditarProduto editarProduto = new EditarProduto(produto);
            editarProduto.start(new Stage());
        }
    }

    private void delete() {
        if(AlertBox.confirmationDelete()) {
            Produto produto = table_product.getSelectionModel().getSelectedItem();
            if (produto == null) {
                AlertBox.selectARecord();
            } else {
                if (ProdutoDAO.deleteByCode(produto.getCodigo())) {
                    AlertBox.deleteCompleted();
                    filter();
                } else {
                    AlertBox.deleteError();
                }
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

        table_item_refresh.setOnAction(action -> {
            filter();
        });

        table_item_edit.setOnAction(action -> {
            edit();
        });

        table_item_delete.setOnAction(action -> {
            delete();
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