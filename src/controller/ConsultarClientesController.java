package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import dao.ClienteDAO;
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
import model.Cliente;
import view.EditarCliente;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarClientesController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldSearchName;

    @FXML
    private TextField fieldSearchCpf;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<Cliente> tableClients;

    @FXML
    private TableColumn<Cliente, String> columnName;

    @FXML
    private TableColumn<Cliente, String> columnCpf;

    @FXML
    private TableColumn<Cliente, String> columnPhoneNumber;

    @FXML
    private TableColumn<Cliente, String> columnEmail;

    @FXML
    private TableColumn<Cliente, String> columnAddress;

    @FXML
    private TableColumn<Cliente, String> columnCity;

    @FXML
    private MenuItem tableItemRefresh;

    @FXML
    private MenuItem tableItemEdit;

    @FXML
    private MenuItem tableItemDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableItemDelete.setVisible(false); // BUG

        blockFullAccess();

        filter();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            filter();
        });

        fieldSearchName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        fieldSearchCpf.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        tableItemRefresh.setOnAction(action -> {
            filter();
        });

        tableItemEdit.setOnAction(action -> {
            edit();
        });

        tableItemDelete.setOnAction(action -> {
            delete();
        });

        Helper.addTextLimiter(fieldSearchName, 40);
        Helper.addTextLimiter(fieldSearchCpf, 11);
    }

    private void blockFullAccess() {
        tableItemDelete.setVisible(Access.isFullAccess());
        tableItemEdit.setVisible(Access.isFullAccess());
    }

    private void filter() {
        String name = fieldSearchName.getText();
        String cpf = fieldSearchCpf.getText();
        boolean filterByName = !name.isBlank();
        boolean filterByCpf = !cpf.isBlank();
        if (filterByName && filterByCpf) {
            fillTable(ClienteDAO.queryByNameOrCpfClients(name, cpf));
        } else if (filterByName && !filterByCpf) {
            fillTable(ClienteDAO.queryByNameClients(name));
        } else if (!filterByName && filterByCpf) {
            fillTable(ClienteDAO.queryByCpfClients(cpf));
        } else {
            fillTable(ClienteDAO.queryAllClients());
        }
    }

    private void edit() {
        Cliente cliente = tableClients.getSelectionModel().getSelectedItem();
        if (cliente == null) {
            AlertBox.selectARecord();
        } else {
            EditarCliente editarCliente = new EditarCliente(cliente);
            editarCliente.start(new Stage());
        }
    }

    private void delete() {
        if (AlertBox.confirmationDelete()) {
            Cliente cliente = tableClients.getSelectionModel().getSelectedItem();
            if (cliente == null) {
                AlertBox.selectARecord();
            } else {
                if (ClienteDAO.deleteByCode(cliente.getCodigo())) {
                    AlertBox.deleteCompleted();
                    filter();
                } else {
                    AlertBox.deleteError();
                }
            }
        }
    }

    private void fillTable(List<Cliente> clientes) {
        columnName.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
        columnCpf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
        columnAddress.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s, %s", data.getValue().getEndereco(), data.getValue().getNumero())));
        columnCity.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s - %s", data.getValue().getCidade(), data.getValue().getUf())));

        ObservableList<Cliente> items = FXCollections.observableArrayList(clientes);
        tableClients.setItems(items);
        tableClients.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}