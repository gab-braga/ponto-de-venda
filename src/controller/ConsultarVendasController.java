package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
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
    private TableView<Venda> tableSales;

    @FXML
    private TableColumn<Venda, String> columnDateHour;

    @FXML
    private TableColumn<Venda, Double> columnValue;

    @FXML
    private TableColumn<Venda, String> columnClient;

    @FXML
    private TableColumn<Venda, String> columnOperator;

    @FXML
    private MenuItem tableItemRefresh;

    @FXML
    private MenuItem tableItemDetails;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        tableItemDetails.setOnAction(action -> {
            opeanWidowDetailsSale();
        });

        Helper.addTextLimiter(fieldSearchDay, 2);
    }

    private void filter() {
        String day = fieldSearchDay.getText();
        String month = fieldSearchMonth.getValue();
        String year = fieldSearchYear.getValue();

        if (isSearchAll(day, month, year)) {
            fillTable(VendaDAO.queryAllRegisters());
        } else {
            if (validateFields(day, month, year)) {
                String dateString = Helper.formatDateByDayMonthYear(day, month, year);
                if (Validator.validateDate(dateString)) {
                    Date date = Helper.parseDate(dateString);
                    fillTable(VendaDAO.querySalesByDate(date));
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

    private boolean validateFields(String day, String month, String year) {
        return !(day.isBlank() || month.isBlank() || year.isBlank());
    }

    private void fillTable(List<Venda> vendas) {
        columnDateHour.setCellValueFactory(data -> new SimpleStringProperty(Helper.formatDateAndTime(data.getValue().getDataHora())));
        columnValue.setCellValueFactory(new PropertyValueFactory<Venda, Double>("valor"));
        columnClient.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNome()));
        columnOperator.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getOperator().getNome()));

        ObservableList<Venda> items = FXCollections.observableArrayList(vendas);
        tableSales.setItems(items);
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    private void opeanWidowDetailsSale() {
        Venda venda = tableSales.getSelectionModel().getSelectedItem();
        if (venda == null) {
            AlertBox.selectARecord();
        } else {
            DetalhesVenda detalhesVenda = new DetalhesVenda(venda);
            detalhesVenda.start(new Stage());
        }
    }
}