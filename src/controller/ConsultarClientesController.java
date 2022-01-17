package controller;

import controller.util.AlertBox;
import controller.util.Helper;
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
import model.Client;
import model.dao.ClientDAO;
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
    private TableView<Client> tableClients;

    @FXML
    private TableColumn<Client, String> columnName;

    @FXML
    private TableColumn<Client, String> columnCpf;

    @FXML
    private TableColumn<Client, String> columnPhoneNumber;

    @FXML
    private TableColumn<Client, String> columnEmail;

    @FXML
    private TableColumn<Client, String> columnAddress;

    @FXML
    private TableColumn<Client, String> columnCity;

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
        ClientDAO dao = new ClientDAO();
        if (filterByName && filterByCpf) {
            fillTable(dao.selectClientByNameOrCPF(name, cpf));
        } else if (filterByName && !filterByCpf) {
            fillTable(dao.selectClientByName(name));
        } else if (!filterByName && filterByCpf) {
            fillTable(dao.selectClientByCPF(cpf));
        } else {
            fillTable(dao.selectAllClients());
        }
    }

    private void edit() {
        Client client = tableClients.getSelectionModel().getSelectedItem();
        if (client == null) {
            AlertBox.selectARecord();
        } else {
            EditarCliente editarCliente = new EditarCliente(client);
            editarCliente.start(new Stage());
        }
    }

    private void delete() {
        if (AlertBox.confirmationDelete()) {
            Client client = tableClients.getSelectionModel().getSelectedItem();
            if (client == null) {
                AlertBox.selectARecord();
            } else {
                ClientDAO dao = new ClientDAO();
                if (dao.deleteByCode(client.getCode())) {
                    AlertBox.deleteCompleted();
                    filter();
                } else {
                    AlertBox.deleteError();
                }
            }
        }
    }

    private void fillTable(List<Client> clients) {
        columnName.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        columnCpf.setCellValueFactory(new PropertyValueFactory<Client, String>("cpf"));
        columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<Client, String>("phone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        columnAddress.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s, %s", data.getValue().getAddress(), data.getValue().getNumber())));
        columnCity.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s - %s", data.getValue().getCity(), data.getValue().getUf())));

        ObservableList<Client> items = FXCollections.observableArrayList(clients);
        tableClients.setItems(items);
        tableClients.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}