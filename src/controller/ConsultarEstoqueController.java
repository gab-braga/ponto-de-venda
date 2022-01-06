package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.EstoqueDAO;
import javafx.beans.property.SimpleIntegerProperty;
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
import model.Estoque;
import view.AlterarEstoque;

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
    private TableView<Estoque> tableStock;

    @FXML
    private TableColumn<Estoque, Integer> columnCodeProduct;

    @FXML
    private TableColumn<Estoque, String> columnDescriptionProduct;

    @FXML
    private TableColumn<Estoque, String> columnTypePacked;

    @FXML
    private TableColumn<Estoque, Integer> columnQuantity;

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
            if (filterByCode && filterByDescription) {
                fillTable(EstoqueDAO.queryByCodeOrDescription(Integer.parseInt(code), description));
            } else if (filterByCode && !filterByDescription) {
                fillTable(EstoqueDAO.queryStockByCode(Integer.parseInt(code)));
            } else if (!filterByCode && filterByDescription) {
                fillTable(EstoqueDAO.queryByDescription(description));
            } else {
                fillTable(EstoqueDAO.queryAllStock());
            }
        } else {
            AlertBox.onlyNumbers();
        }
    }

    private void fillTable(List<Estoque> estoque) {
        columnCodeProduct.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProduto().getCodigo()).asObject());
        columnDescriptionProduct.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescricao()));
        columnTypePacked.setCellValueFactory(new PropertyValueFactory<Estoque, String>("tipoEmbalado"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<Estoque, Integer>("quantidade"));

        ObservableList<Estoque> items = FXCollections.observableArrayList(estoque);
        tableStock.setItems(items);
    }

    private void openAlterationWindow() {
        Estoque estoque = tableStock.getSelectionModel().getSelectedItem();
        if (estoque == null) {
            AlertBox.selectARecord();
        } else {
            AlterarEstoque alterarEstoque = new AlterarEstoque(estoque);
            alterarEstoque.start(new Stage());
        }
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
