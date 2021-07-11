package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Cliente;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultarClientesController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_name;

    @FXML
    private TextField field_search_cpf;

    @FXML
    private TextField field_search_city;

    @FXML
    private TableView<Cliente> table_clients;

    @FXML
    private TableColumn<String, Cliente> column_name;

    @FXML
    private TableColumn<String, Cliente> column_cpf;

    @FXML
    private TableColumn<String, Cliente> column_phone;

    @FXML
    private TableColumn<String, Cliente> column_email;

    @FXML
    private TableColumn<String, Cliente> column_address;

    @FXML
    private TableColumn<String, Cliente> column_city;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}