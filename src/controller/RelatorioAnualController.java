package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Operation;
import model.Report;
import model.dao.OperationDAO;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioAnualController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ComboBox<String> fieldYear;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Operation> tableYearlyReport;

    @FXML
    private TableColumn<Report, String> tableColumnYear;

    @FXML
    private TableColumn<Report, Double> tableColumnDeposit;

    @FXML
    private TableColumn<Report, Double> tableColumnOut;

    @FXML
    private TableColumn<Report, Double> tableColumnTotal;

    @FXML
    private MenuItem tableItemRefresh;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Helper.fillFieldYear(fieldYear);

        filter();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            filter();
        });

        fieldYear.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        tableItemRefresh.setOnAction(action -> {
            filter();
        });
    }

    private void filter() {
        String year = fieldYear.getValue();
        OperationDAO dao = new OperationDAO();
        if (isSearchAll(year)) {
            fillTable(dao.selectOperationsPerYear());
        } else {
            if (Validator.validateFields(year)) {
                String dateString = Helper.formatDateByYear(year);
                Date date = Helper.parseDateYear(dateString);
                fillTable(dao.selectOperationsPerYearByYear(date));
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    private boolean isSearchAll(String year) {
        return (year.isBlank());
    }

    private void fillTable(List<Operation> operations) {
        if(!operations.isEmpty()) {
            tableColumnYear.setCellValueFactory(data -> new SimpleStringProperty(Helper.extractYearFromDate(data.getValue().getTimestamp())));
            tableColumnDeposit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValueSale()).asObject());
            tableColumnOut.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValueAcquisition()).asObject());
            tableColumnTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValueTotal()).asObject());
        }
        ObservableList<Operation> items = FXCollections.observableArrayList(operations);
        tableYearlyReport.setItems(items);
        tableYearlyReport.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}