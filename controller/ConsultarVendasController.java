package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Venda;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ConsultarVendasController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_day;

    @FXML
    private ComboBox<String> field_search_month;

    @FXML
    private TextField field_search_client;

    @FXML
    private TableView<Venda> table_sales;

    @FXML
    private TableColumn<Venda, String> column_client;

    @FXML
    private TableColumn<Venda, Double> column_value;

    @FXML
    private TableColumn<Venda, Date> column_date_hour;

    @FXML
    private TableColumn<Venda, String> column_seller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}