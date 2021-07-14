package controller;

import dao.ClienteDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import view.ConsultarClientes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarClientesController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_name;

    @FXML
    private TextField field_search_cpf;

    @FXML
    private Button btn_close;

    @FXML
    private TableView<Cliente> table_clients;

    @FXML
    private TableColumn<Cliente, String> column_name;

    @FXML
    private TableColumn<Cliente, String> column_cpf;

    @FXML
    private TableColumn<Cliente, String> column_phone;

    @FXML
    private TableColumn<Cliente, String> column_email;

    @FXML
    private TableColumn<Cliente, String> column_address;

    @FXML
    private TableColumn<Cliente, String> column_city;

    private void fillTable(List<Cliente> clientes) {
        column_name.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        column_cpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        column_phone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
        column_email.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
        column_address.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s, %s", data.getValue().getEndereco(), data.getValue().getNumero())));
        column_city.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s - %s", data.getValue().getCidade(), data.getValue().getUf())));

        ObservableList<Cliente> items = FXCollections.observableArrayList(clientes);
        table_clients.setItems(items);
        table_clients.refresh();
    }

    private void filter() {
        String name = field_search_name.getText();
        String cpf = field_search_cpf.getText();
        boolean filterByName = !name.isEmpty();
        boolean filterByCpf = !cpf.isEmpty();
        ClienteDAO clienteDAO = new ClienteDAO();
        if(filterByName && filterByCpf) {
           fillTable(clienteDAO.queryByNameOrCpfClients(name, cpf));
        }
        else if(filterByName && !filterByCpf) {
            fillTable(clienteDAO.queryByNameClients(name));
        }
        else if(!filterByName && filterByCpf) {
            fillTable(clienteDAO.queryByCpfClients(cpf));
        }
        else {
            fillTable(clienteDAO.queryAllClients());
        }
    }

    private void close() {
        ConsultarClientes.getWindow().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        field_search_name.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_cpf.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_name.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_search_name.getText().length() >= maxCharacters) event.consume();
        });

        field_search_cpf.setOnKeyTyped(event -> {
            int maxCharacters = 11;
            if(field_search_name.getText().length() >= maxCharacters) event.consume();
        });

    }
}