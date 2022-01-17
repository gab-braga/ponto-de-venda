package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Client;
import model.dao.ClientDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarClienteController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldCpf;

    @FXML
    private TextField fieldPhoneNumber;

    @FXML
    private TextField fieldEmail;

    @FXML
    private TextField field_address;

    @FXML
    private TextField fieldNumber;

    @FXML
    private TextField fieldCity;

    @FXML
    private TextField fieldUf;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    private Client clientEdit;

    public void fillFields(Client client) {
        this.clientEdit = client;
        fieldName.setText(client.getName());
        fieldCpf.setText(client.getCpf());
        fieldPhoneNumber.setText(client.getPhone());
        fieldEmail.setText(client.getEmail());
        field_address.setText(client.getAddress());
        fieldNumber.setText(client.getNumber());
        fieldCity.setText(client.getCity());
        fieldUf.setText(client.getUf());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            edit();
        });

        fieldName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldCpf.requestFocus();
        });

        fieldCpf.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldPhoneNumber.requestFocus();
        });

        fieldPhoneNumber.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldEmail.requestFocus();
        });

        fieldEmail.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                field_address.requestFocus();
        });

        field_address.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldNumber.requestFocus();
        });

        fieldNumber.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldCity.requestFocus();
        });

        fieldCity.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldUf.requestFocus();
        });

        fieldUf.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                edit();
        });

        Helper.addTextLimiter(fieldName, 40);
        Helper.addTextLimiter(fieldCpf, 11);
        Helper.addTextLimiter(fieldPhoneNumber, 15);
        Helper.addTextLimiter(fieldEmail, 50);
        Helper.addTextLimiter(field_address, 80);
        Helper.addTextLimiter(fieldNumber, 5);
        Helper.addTextLimiter(fieldCity, 40);
        Helper.addTextLimiter(fieldUf, 2);
    }

    private void edit() {
        Client client = getModel();
        if(client != null) {
            ClientDAO dao = new ClientDAO();
            if (dao.update(client)) {
                AlertBox.editionCompleted();
                closeWindow();
            } else {
                AlertBox.editionError();
            }
        }
    }

    private Client getModel() {
        Client client = null;
        String nome = fieldName.getText();
        String cpf = fieldCpf.getText();
        String telefone = fieldPhoneNumber.getText();
        String email = fieldEmail.getText();
        String endereco = field_address.getText();
        String numero = fieldNumber.getText();
        String cidade = fieldCity.getText();
        String uf = fieldUf.getText();

        if (Validator.validateFields(nome, cpf, telefone, email, endereco, numero, cidade, uf)) {
            client = client = new Client(clientEdit.getCode(), nome, cpf, telefone, email, endereco, numero, cidade, uf);
        } else {
            AlertBox.fillAllFields();
        }
        return client;
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}