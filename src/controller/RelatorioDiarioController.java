package controller;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioDiarioController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_day;

    @FXML
    private ComboBox<String> field_month;

    @FXML
    private ComboBox<String> field_year;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_close;

    @FXML
    private TableView<Caixa> table_daily_report;

    @FXML
    private TableColumn<Caixa, String> table_column_date;

    @FXML
    private TableColumn<Caixa, Double> table_column_deposit;

    @FXML
    private TableColumn<Caixa, Double> table_column_out;

    @FXML
    private TableColumn<Caixa, Double> table_column_total;

    @FXML
    private MenuItem table_item_refresh;

    private void fillFieldMonth() {
        List<String> months = Helper.getListMonths();
        ObservableList<String> items = FXCollections.observableArrayList(months);
        field_month.setItems(items);
        field_month.setValue("");
    }

    private void fillFieldYear() {
        List<String> years = Helper.getListYears();
        ObservableList<String> items = FXCollections.observableArrayList(years);
        field_year.setItems(items);
        field_year.setValue("");
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private boolean validateFields(String day, String month, String year) {
        return !(day.isEmpty() || month.isEmpty() || year.isEmpty());
    }

    private boolean isSearchAll(String day, String month, String year) {
        return (day.isEmpty() && month.isEmpty() && year.isEmpty());
    }

    private void fillTable(List<Caixa> caixas) {
        table_column_date.setCellValueFactory(data -> new SimpleStringProperty(Helper.getDateStringFormatted(data.getValue().getData())));
        table_column_deposit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorEntrada()).asObject());
        table_column_out.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorSaida()).asObject());
        table_column_total.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()).asObject());

        ObservableList<Caixa> items = FXCollections.observableArrayList(caixas);
        table_daily_report.setItems(items);
    }

    private void filter() {
        String day = field_day.getText();
        String month = field_month.getValue();
        String year = field_year.getValue();

        if (isSearchAll(day, month, year)) {
            fillTable(CaixaDAO.queryAllDailyBoxs());
        } else {
            if (validateFields(day, month, year)) {
                String dateString = Helper.getDateStringByDayMonthYear(day, month, year);
                if (Helper.validateDate(dateString)) {
                    Date date = Helper.getDateFormattedDayMonthYear(dateString);
                    fillTable(CaixaDAO.queryBoxByDate(date));
                } else {
                    AlertBox.dateInvalided();
                }
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillFieldMonth();

        fillFieldYear();

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        btn_search.setOnMouseClicked(click -> {
            filter();
        });

        field_day.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_month.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_year.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        table_item_refresh.setOnAction(action -> {
            filter();
        });

        Helper.addTextLimiter(field_day, 2);
    }
}