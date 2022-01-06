package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
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
import javafx.stage.Stage;
import model.Saida;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarSaidasController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldSearchDay;

    @FXML
    private ComboBox<String> fieldSearchMonth;

    @FXML
    private ComboBox<String> fieldSearchYear;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<Saida> tableExits;

    @FXML
    private TableColumn<Saida, String> columnDateHour;

    @FXML
    private TableColumn<Saida, Double> columnValue;

    @FXML
    private TableColumn<Saida, String> columnReason;

    @FXML
    private TableColumn<Saida, String> columnOperator;

    @FXML
    private MenuItem tableItemRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Helper.fillFieldMonth(fieldSearchMonth);
        Helper.fillFieldYear(fieldSearchYear);

        filter();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            filter();
        });

        fieldSearchDay.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        fieldSearchMonth.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        fieldSearchYear.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        tableItemRefresh.setOnAction(action -> {
            filter();
        });

        Helper.addTextLimiter(fieldSearchDay, 2);
    }

    private void filter() {
        String day = fieldSearchDay.getText();
        String month = fieldSearchMonth.getValue();
        String year = fieldSearchYear.getValue();

        if (isSearchAll(day, month, year)) {
            fillTable(SaidaDAO.queryAllExits());
        } else {
            if (validateFields(day, month, year)) {
                String dateString = Helper.formatDateByDayMonthYear(day, month, year);
                if (Validator.validateDate(dateString)) {
                    Date date = Helper.parseDate(dateString);
                    fillTable(SaidaDAO.queryExitsByDate(date));
                } else {
                    AlertBox.dateInvalided();
                }
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    private boolean isSearchAll(String day, String month, String year) {
        return (day.isBlank() && month.isBlank() && year.isBlank());
    }

    private void fillTable(List<Saida> saidas) {
        columnDateHour.setCellValueFactory(data -> new SimpleStringProperty(Helper.formatDateAndTime(data.getValue().getDataHora())));
        columnValue.setCellValueFactory(new PropertyValueFactory<Saida, Double>("valor"));
        columnReason.setCellValueFactory(new PropertyValueFactory<Saida, String>("motivo"));
        columnOperator.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOperador().getNome()));

        ObservableList<Saida> items = FXCollections.observableArrayList(saidas);
        tableExits.setItems(items);
    }

    private boolean validateFields(String day, String month, String year) {
        return !(day.isBlank() || month.isBlank() || year.isBlank());
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}