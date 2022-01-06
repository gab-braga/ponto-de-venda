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
    private TableView<Caixa> tableYearlyReport;

    @FXML
    private TableColumn<Caixa, String> tableColumnYear;

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

        if (isSearchAll(year)) {
            fillTable(CaixaDAO.queryAllYearlyBoxs());
        } else {
            if (Validator.validateFields(year)) {
                String dateString = Helper.formatDateByYear(year);
                Date date = Helper.parseDateYear(dateString);
                fillTable(CaixaDAO.queryBoxByYear(date));
            } else {
                AlertBox.fillAllFields();
            }
        }
    }

    private boolean isSearchAll(String year) {
        return (year.isBlank());
    }

    private void fillTable(List<Caixa> caixas) {
        tableColumnYear.setCellValueFactory(data -> new SimpleStringProperty(Helper.extractYearFromDate(data.getValue().getData())));
        tableColumnDeposit.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorEntrada()).asObject());
        tableColumnOut.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorSaida()).asObject());
        tableColumnTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()).asObject());

        ObservableList<Caixa> items = FXCollections.observableArrayList(caixas);
        tableYearlyReport.setItems(items);
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}