package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.Helper;
import com.github.fgabrielbraga.controller.util.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.github.fgabrielbraga.model.Product;
import com.github.fgabrielbraga.model.dao.ProductDAO;
import com.github.fgabrielbraga.model.dao.StockDAO;
import com.github.fgabrielbraga.view.EditarProduto;

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
    private TableView<Product> tableProduct;

    @FXML
    private TableColumn<Product, Integer> columnCode;

    @FXML
    private TableColumn<Product, String> columnDescription;

    @FXML
    private TableColumn<Product, Double> columnSaleValue;

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
            ProductDAO dao = new ProductDAO();
            if (!filterByCode && filterByDescription) {
                fillTable(dao.selectProductByDescription(description));
            } else if (filterByCode && !filterByDescription) {
                fillTable(dao.selectProductByCode(Long.parseLong(code)));
            } else if (filterByCode && filterByDescription) {
                fillTable(dao.selectProductByCodeOrDescription(Long.parseLong(code), description));
            } else {
                fillTable(dao.selectAllProducts());
            }
        }
    }

    private void fillTable(List<Product> products) {
        columnCode.setCellValueFactory(new PropertyValueFactory<Product, Integer>("code"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        columnSaleValue.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        ObservableList<Product> items = FXCollections.observableArrayList(products);
        tableProduct.setItems(items);
        tableProduct.refresh();
    }

    private void openEditionWindow() {
        Product product = tableProduct.getSelectionModel().getSelectedItem();
        if (product == null) {
            AlertBox.selectARecord();
        } else {
            EditarProduto editarProduto = new EditarProduto(product);
            editarProduto.start(new Stage());
        }
    }

    private void openDelitionWindow() {
        if (AlertBox.confirmationDelete()) {
            Product product = tableProduct.getSelectionModel().getSelectedItem();
            if (product == null) {
                AlertBox.selectARecord();
            } else {
                StockDAO stockDAO = new StockDAO();
                if (stockDAO.selectStockByCode(product.getCode()).size() == 0) {
                    ProductDAO dao = new ProductDAO();
                    if (dao.deleteByCode(product.getCode())) {
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