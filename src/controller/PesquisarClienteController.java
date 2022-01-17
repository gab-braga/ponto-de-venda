package controller;

import controller.util.AlertBox;
import controller.util.SearchGuide;
import controller.util.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Client;
import model.dao.ClientDAO;
import view.PesquisarCliente;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PesquisarClienteController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldNameClient;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private ListView<Client> listView;

    private SearchGuide searchGuide;

    public void setSearchGuide(SearchGuide searchGuide) {
        this.searchGuide = searchGuide;
    }

    public void setClientName(String clientName) {
        fieldNameClient.setText(clientName);
        searchItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchItem();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            selectItemListView();
        });

        fieldNameClient.setOnKeyReleased(keyEvent -> {
            searchItem();
        });

        listView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                selectItemListView();
        });

        Helper.addTextLimiter(fieldNameClient, 40);
    }

    private void searchItem() {
        String name = fieldNameClient.getText();
        ClientDAO dao = new ClientDAO();
        if (name == null || name.isBlank()) {
            fillListView(dao.selectAllClients());
        } else {
            fillListView(dao.selectClientByName(name));
        }
    }

    private void fillListView(List<Client> items) {
        ObservableList groupByClients = FXCollections.observableArrayList(items);
        listView.setItems(groupByClients);
    }

    private void selectItemListView() {
        Client client = listView.getSelectionModel().getSelectedItem();
        if (client == null) {
            AlertBox.selectARecord();
        } else {
            this.searchGuide.returnData(client);
            closeWindow();
        }

    }

    private void closeWindow() {
        PesquisarCliente.getWindow().close();
    }
}
