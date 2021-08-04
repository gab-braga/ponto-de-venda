package controller;

import dao.VendaDAO;
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
import model.Venda;
import view.DetalhesVenda;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarVendasController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_day;

    @FXML
    private ComboBox<String> field_search_month;

    @FXML
    private ComboBox<String> field_search_year;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_search;

    @FXML
    private TableView<Venda> table_sales;

    @FXML
    private TableColumn<Venda, String> column_date_hour;

    @FXML
    private TableColumn<Venda, Double> column_value;

    @FXML
    private TableColumn<Venda, String> column_client;

    @FXML
    private TableColumn<Venda, String> column_operator;

    @FXML
    private MenuItem table_item_refresh;

    @FXML
    private MenuItem table_item_details;

    private void fillFieldYear() {
        List<String> years = Helper.getListYears();
        ObservableList<String> items = FXCollections.observableArrayList(years);
        field_search_year.setItems(items);
        field_search_year.setValue("");
    }

    private void fillFieldMonth() {
        List<String> months = Helper.getListMonths();
        ObservableList<String> items = FXCollections.observableArrayList(months);
        field_search_month.setItems(items);
        field_search_month.setValue("");
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private void detailsSale() {
        Venda venda = table_sales.getSelectionModel().getSelectedItem();
        if (venda == null) {
            AlertBox.selectARecord();
        } else {
            DetalhesVenda detalhesVenda = new DetalhesVenda(venda);
            detalhesVenda.start(new Stage());
        }
    }

    private boolean validateFields(String day, String month, String year) {
        return !(day.isEmpty() || month.isEmpty() || year.isEmpty());
    }

    private boolean isSearchAll(String day, String month, String year) {
        return (day.isEmpty() && month.isEmpty() && year.isEmpty());
    }

    private void fillTable(List<Venda> vendas) {
        column_date_hour.setCellValueFactory(data -> new SimpleStringProperty(Helper.getDateTimeStringFormatted(data.getValue().getDataHora())));
        column_value.setCellValueFactory(new PropertyValueFactory<Venda, Double>("valor"));
        column_client.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNome()));
        column_operator.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVendedor().getNome()));

        ObservableList<Venda> items = FXCollections.observableArrayList(vendas);
        table_sales.setItems(items);
    }

    private void filter() {
        String day = field_search_day.getText();
        String month = field_search_month.getValue();
        String year = field_search_year.getValue();

        if (isSearchAll(day, month, year)) {
            fillTable(VendaDAO.queryAllRegisters());
        } else {
            if (validateFields(day, month, year)) {
                String dateString = Helper.getDateStringByDayMonthYear(day, month, year);
                if (Helper.validateDate(dateString)) {
                    Date date = Helper.getDateFormattedDayMonthYear(dateString);
                    fillTable(VendaDAO.querySalesByDate(date));
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

        fillFieldYear();

        fillFieldMonth();

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        btn_search.setOnMouseClicked(click -> {
            filter();
        });

        field_search_day.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_month.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_year.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        table_item_refresh.setOnAction(action -> {
            filter();
        });

        table_item_details.setOnAction(action -> {
            detailsSale();
        });

        Helper.addTextLimiter(field_search_day, 2);
    }
}