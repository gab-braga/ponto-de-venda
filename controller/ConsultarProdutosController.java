package controller;

import dao.EstoqueDAO;
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
import model.Produto;
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
    private Button btn_search;

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

    private void blockFullAccess() {
        table_item_delete.setVisible(Access.isFullAccess());
        table_item_edit.setVisible(Access.isFullAccess());
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

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
        if (Helper.validateInteger(code) || !filterByCode) {
            if (!filterByCode && filterByDescription) {
                fillTable(ProdutoDAO.queryByDescriptionProducts(description));
            } else if (filterByCode && !filterByDescription) {
                fillTable(ProdutoDAO.queryProductByCode(Integer.parseInt(code)));
            } else if (filterByCode && filterByDescription) {
                fillTable(ProdutoDAO.queryByCodeOrDescriptionProducts(Integer.parseInt(code), description));
            } else {
                fillTable(ProdutoDAO.queryAllProducts());
            }
        }
    }

    private void edit() {
        Produto produto = table_product.getSelectionModel().getSelectedItem();
        if (produto == null) {
            AlertBox.selectARecord();
        } else {
            EditarProduto editarProduto = new EditarProduto(produto);
            editarProduto.start(new Stage());
        }
    }

    private void delete() {
        if (AlertBox.confirmationDelete()) {
            Produto produto = table_product.getSelectionModel().getSelectedItem();
            if (produto == null) {
                AlertBox.selectARecord();
            } else {
                if (EstoqueDAO.queryStockByCode(produto.getCodigo()).size() == 0) {
                    if (ProdutoDAO.deleteByCode(produto.getCodigo())) {
                        AlertBox.deleteCompleted();
                        filter();
                    } else {
                        AlertBox.deleteError();
                    }
                } else {
                    AlertBox.productStillExistsInStock();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blockFullAccess();

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        btn_search.setOnMouseClicked(click -> {
            filter();
        });

        field_search_code.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_description.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
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

        Helper.addTextLimiter(field_search_code, 40);
        Helper.addTextLimiter(field_search_description, 100);
    }
}