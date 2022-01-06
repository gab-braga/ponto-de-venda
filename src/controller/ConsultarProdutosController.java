package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
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
    private AnchorPane rootPane;

    @FXML
    private TextField fieldSearchCode;

    @FXML
    private TextField fieldSearchDescription;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<Produto> tableProduct;

    @FXML
    private TableColumn<Produto, Integer> columnCode;

    @FXML
    private TableColumn<Produto, String> columnDescription;

    @FXML
    private TableColumn<Produto, Double> columnSaleValue;

    @FXML
    private MenuItem tableItemRefresh;

    @FXML
    private MenuItem tableItemEdit;

    @FXML
    private MenuItem tableItemDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blockFullAccess();

        filter();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            filter();
        });

        fieldSearchCode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        fieldSearchDescription.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        tableItemRefresh.setOnAction(action -> {
            filter();
        });

        tableItemEdit.setOnAction(action -> {
            openEditionWindow();
        });

        tableItemDelete.setOnAction(action -> {
            openDelitionWindow();
        });

        Helper.addTextLimiter(fieldSearchCode, 40);
        Helper.addTextLimiter(fieldSearchDescription, 100);
    }

    private void blockFullAccess() {
        tableItemDelete.setVisible(Access.isFullAccess());
        tableItemEdit.setVisible(Access.isFullAccess());
    }

    private void filter() {
        String code = fieldSearchCode.getText();
        String description = fieldSearchDescription.getText();
        boolean filterByCode = !code.isBlank();
        boolean filterByDescription = !description.isBlank();
        if (Validator.validateInteger(code) || !filterByCode) {
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

    private void fillTable(List<Produto> produtos) {
        columnCode.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("codigo"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
        columnSaleValue.setCellValueFactory(new PropertyValueFactory<Produto, Double>("valorVenda"));

        ObservableList<Produto> items = FXCollections.observableArrayList(produtos);
        tableProduct.setItems(items);
        tableProduct.refresh();
    }

    private void openEditionWindow() {
        Produto produto = tableProduct.getSelectionModel().getSelectedItem();
        if (produto == null) {
            AlertBox.selectARecord();
        } else {
            EditarProduto editarProduto = new EditarProduto(produto);
            editarProduto.start(new Stage());
        }
    }

    private void openDelitionWindow() {
        if (AlertBox.confirmationDelete()) {
            Produto produto = tableProduct.getSelectionModel().getSelectedItem();
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

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}