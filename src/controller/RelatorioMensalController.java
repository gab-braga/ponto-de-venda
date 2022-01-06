package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.CaixaDAO;
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
import model.Caixa;

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
    private TableView<Caixa> tableMonthlyReport;

    @FXML
    private TableColumn<Caixa, String> tableColumnDate;

    @FXML
    private TableColumn<Caixa, Double> tableColumnDeposit;

    @FXML
    private TableColumn<Caixa, Double> tableColumnOut;

    @FXML
    private TableColumn<Caixa, Double> tableColumnTotal;

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

        if (isSearchAll(month, year)) {
            fillTable(CaixaDAO.queryAllMonthlyBoxs());
        } else {
            if (Validator.validateFields(month, year)) {
                String dateString = Helper.formatDateStringByMonthYear(month, year);
                Date date = Helper.parseDateMonthAndYear(dateString);
                fillTable(CaixaDAO.queryBoxByMonthYear(date));
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    private boolean isSearchAll(String month, String year) {
        return (month.isBlank() && year.isBlank());
    }

    private void fillTable(List<Caixa> caixas) {
        tableColumnDate.setCellValueFactory(data -> new SimpleStringProperty(Helper.extractMonthAndYearFromDate(data.getValue().getData())));
        tableColumnDeposit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorEntrada()).asObject());
        tableColumnOut.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorSaida()).asObject());
        tableColumnTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()).asObject());

        ObservableList<Caixa> items = FXCollections.observableArrayList(caixas);
        tableMonthlyReport.setItems(items);
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}