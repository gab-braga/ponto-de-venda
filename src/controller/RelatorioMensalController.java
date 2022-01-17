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

public class RelatorioMensalController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ComboBox<String> fieldMonth;

    @FXML
    private ComboBox<String> fieldYear;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<Operation> tableMonthlyReport;

    @FXML
    private TableColumn<Report, String> tableColumnDate;

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

        Helper.fillFieldMonth(fieldMonth);
        Helper.fillFieldYear(fieldYear);

        filter();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            filter();
        });

        fieldMonth.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
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
        String month = fieldMonth.getValue();
        String year = fieldYear.getValue();
        OperationDAO dao = new OperationDAO();
        if (isSearchAll(month, year)) {
            fillTable(dao.selectOperationsPerMonth());
        } else {
            if (Validator.validateFields(month, year)) {
                String dateString = Helper.formatDateStringByMonthYear(month, year);
                Date date = Helper.parseDateMonthAndYear(dateString);
                fillTable(dao.selectOperationsPerMonthByMonth(date));
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    private boolean isSearchAll(String month, String year) {
        return (month.isBlank() && year.isBlank());
    }

    private void fillTable(List<Operation> operations) {
        if(!operations.isEmpty()) {
            tableColumnDate.setCellValueFactory(data -> new SimpleStringProperty(Helper.extractMonthAndYearFromDate(data.getValue().getTimestamp())));
            tableColumnDeposit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValueSale()).asObject());
            tableColumnOut.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValueAcquisition()).asObject());
            tableColumnTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValueTotal()).asObject());
        }
        ObservableList<Operation> items = FXCollections.observableArrayList(operations);
        tableMonthlyReport.setItems(items);
        tableMonthlyReport.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}