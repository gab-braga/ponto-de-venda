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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class RelatorioAnualController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> field_year;

    @FXML
    private Button btn_search;

    @FXML
    private Button btn_close;

    @FXML
    private TableView<Caixa> table_yearly_report;

    @FXML
    private TableColumn<Caixa, String> table_column_year;

    @FXML
    private TableColumn<Caixa, Double> table_column_deposit;

    @FXML
    private TableColumn<Caixa, Double> table_column_out;

    @FXML
    private TableColumn<Caixa, Double> table_column_total;

    @FXML
    private MenuItem table_item_refresh;

    private void fillFieldYear() {
        List<String> years = Helper.getListYears();
        ObservableList<String> items = FXCollections.observableArrayList(years);
        field_year.setItems(items);
        field_year.setValue("");
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private boolean validateFields(String year) {
        return !(year.isEmpty());
    }

    private boolean isSearchAll(String year) {
        return (year.isEmpty());
    }

    private void fillTable(List<Caixa> caixas) {
        table_column_year.setCellValueFactory(data -> new SimpleStringProperty(Helper.getDateStringByYear(data.getValue().getData())));
        table_column_deposit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorEntrada()).asObject());
        table_column_out.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorSaida()).asObject());
        table_column_total.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()).asObject());

        ObservableList<Caixa> items = FXCollections.observableArrayList(caixas);
        table_yearly_report.setItems(items);
    }

    private void filter() {
        String year = field_year.getValue();

        if (isSearchAll(year)) {
            fillTable(CaixaDAO.queryAllYearlyBoxs());
        } else {
            if (validateFields(year)) {
                String dateString = Helper.getDateStringByYear(year);
                Date date = Helper.getDateFormattedYear(dateString);
                fillTable(CaixaDAO.queryBoxByYear(date));
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillFieldYear();

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        btn_search.setOnMouseClicked(click -> {
            filter();
        });

        field_year.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        table_item_refresh.setOnAction(action -> {
            filter();
        });
    }
}