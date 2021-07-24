package controller;

import dao.ClienteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Cliente;
import view.PesquisarCliente;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PesquisarClienteController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_name_client;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_select;

    @FXML
    private ListView<Cliente> list_clients;

    private CaixaController caixaController;

    private void search() {
        String name = field_name_client.getText();
        if(name == null || name.isEmpty()) {
            fillList(ClienteDAO.queryAllClients());
        }
        else {
            fillList(ClienteDAO.queryByNameClients(name));
        }
    }

    private void fillList(List<Cliente> clientes) {
        ObservableList items = FXCollections.observableArrayList(clientes);
        list_clients.setItems(items);
    }

    private void close() {
        PesquisarCliente.getWindow().close();
    }

    private void selectClient() {
        Cliente cliente = list_clients.getSelectionModel().getSelectedItem();
        if(cliente == null) {
            AlertBox.selectARecord();
        }
        else {
            this.caixaController.insertAndFillClient(cliente);
            close();
        }

    }

    public void setCaixaController(CaixaController caixaController) {
        this.caixaController = caixaController;
    }

    public void setClientName(String clientName) {
        field_name_client.setText(clientName);
        search();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search();

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_select.setOnMouseClicked(click -> {
            selectClient();
        });

        field_name_client.setOnKeyReleased(keyEvent -> {
            search();
        });

        list_clients.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                selectClient();
        });

        Helper.addTextLimiter(field_name_client, 40);
    }
}
