package controller;

import dao.SaidaDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Saida;
import view.ConsultarSaidas;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarSaidasController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_day;

    @FXML
    private ComboBox<String> field_search_month;

    @FXML
    private TextField field_search_year;

    @FXML
    private Button btn_close;

    @FXML
    private TableView<Saida> table_exits;

    @FXML
    private TableColumn<Saida, String> column_date_hour;

    @FXML
    private TableColumn<Saida, Double> column_value;

    @FXML
    private TableColumn<Saida, String> column_reason;

    @FXML
    private TableColumn<Saida, String> column_operator;

    @FXML
    private MenuItem table_item_refresh;

    @FXML
    private MenuItem table_item_edit;

    @FXML
    private MenuItem table_item_delete;

    private boolean validateFields(String day, String month, String year) {
        return !(day.isEmpty() || month.isEmpty() || year.isEmpty());
    }

    private boolean isSearchAll(String day, String month, String year) {
        return (day.isEmpty() && month.isEmpty() && year.isEmpty());
    }

    private void fillTable(List<Saida> saidas) {
        column_date_hour.setCellValueFactory(data -> new SimpleStringProperty(Helper.getDateTimeStringFormatted(data.getValue().getDataHora())));
        column_value.setCellValueFactory(new PropertyValueFactory<Saida, Double>("valor"));
        column_reason.setCellValueFactory(new PropertyValueFactory<Saida, String>("motivo"));
        column_operator.setCellValueFactory(new PropertyValueFactory<Saida, String>("operador"));

        ObservableList<Saida> items = FXCollections.observableArrayList(saidas);
        table_exits.setItems(items);
    }

    private void filter() {
        String day = field_search_day.getText();
        String month = field_search_month.getValue();
        String year = field_search_year.getText();

        if(isSearchAll(day, month, year)) {
            fillTable(SaidaDAO.queryAllExits());
        }
        else {
            if(validateFields(day, month, year)) {
                String dateString = Helper.getDateStringByDayMonthYear(day, month, year);
                if(Helper.validateDate(dateString)) {
                    Date date = Helper.getDateFormatted(dateString);
                    fillTable(SaidaDAO.queryExitsByDate(date));
                }
                else {
                    AlertBox.dateInvalided();
                }
            }
            else {
                AlertBox.fillAllFields();
            }
        }
    }

    private void fillFieldMonth() {
        List<String> months = Helper.getListMonths();
        ObservableList<String> items = FXCollections.observableArrayList(months);
        field_search_month.setItems(items);
        field_search_month.setValue("");
    }

    private void close() {
        ConsultarSaidas.getWindow().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillFieldMonth();

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        field_search_day.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_search_month.requestFocus();
        });

        field_search_month.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_search_year.requestFocus();
        });

        field_search_year.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_day.setOnKeyTyped(event -> {
            int maxCharacters = 2;
            if(field_search_day.getText().length() >= maxCharacters) event.consume();
        });

        field_search_year.setOnKeyTyped(event -> {
            int maxCharacters = 4;
            if(field_search_year.getText().length() >= maxCharacters) event.consume();
        });
    }
}