package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.Helper;
import com.github.fgabrielbraga.controller.util.Validator;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.github.fgabrielbraga.model.Stock;
import com.github.fgabrielbraga.model.dao.StockDAO;
import com.github.fgabrielbraga.view.AlterarEstoque;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarEstoqueController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldSearchCodeProduct;

    @FXML
    private TextField fieldSearchDescriptionProduct;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<Stock> tableStock;

    @FXML
    private TableColumn<Stock, Long> columnCodeProduct;

    @FXML
    private TableColumn<Stock, String> columnDescriptionProduct;

    @FXML
    private TableColumn<Stock, String> columnTypePacked;

    @FXML
    private TableColumn<Stock, Integer> columnQuantity;

    @FXML
    private MenuItem tableItemRefresh;

    @FXML
    private MenuItem tableItemAdd;

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

        fieldSearchCodeProduct.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        fieldSearchDescriptionProduct.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        tableItemRefresh.setOnAction(action -> {
            filter();
        });

        tableItemAdd.setOnAction(action -> {
            openAlterationWindow();
        });

        Helper.addTextLimiter(fieldSearchCodeProduct, 40);
        Helper.addTextLimiter(fieldSearchDescriptionProduct, 100);
    }

    private void blockFullAccess() {
        tableItemAdd.setVisible(Access.isFullAccess());
    }

    private void filter() {
        String code = fieldSearchCodeProduct.getText();
        String description = fieldSearchDescriptionProduct.getText();
        boolean filterByCode = !code.isBlank();
        boolean filterByDescription = !description.isBlank();
        if (Validator.validateInteger(code) || !filterByCode) {
            StockDAO dao = new StockDAO();
            if (filterByCode && filterByDescription) {
                fillTable(dao.selectStockByCodeOrDescription(Long.parseLong(code), description));
            } else if (filterByCode && !filterByDescription) {
                fillTable(dao.selectStockByCode((Long.parseLong(code))));
            } else if (!filterByCode && filterByDescription) {
                fillTable(dao.selectStockByDescription(description));
            } else {
                fillTable(dao.selectAllStock());
            }
        } else {
            AlertBox.onlyNumbers();
        }
    }

    private void fillTable(List<Stock> stock) {
        columnCodeProduct.setCellValueFactory(data -> new SimpleLongProperty(data.getValue().getProduto().getCode()).asObject());
        columnDescriptionProduct.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescription()));
        columnTypePacked.setCellValueFactory(new PropertyValueFactory<Stock, String>("unity"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<Stock, Integer>("quantity"));

        ObservableList<Stock> items = FXCollections.observableArrayList(stock);
        tableStock.setItems(items);
    }

    private void openAlterationWindow() {
        Stock stock = tableStock.getSelectionModel().getSelectedItem();
        if (stock == null) {
            AlertBox.selectARecord();
        } else {
            AlterarEstoque alterarEstoque = new AlterarEstoque(stock);
            alterarEstoque.start(new Stage());
        }
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
