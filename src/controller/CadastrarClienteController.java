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

public class CadastrarClienteController implements Initializable {

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
    private TextField fieldAddress;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnSubmit.setOnMouseClicked(click -> {
            register();
        });

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
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
                fieldAddress.requestFocus();
        });

        fieldAddress.setOnKeyPressed(keyEvent -> {
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
                register();
        });

        Helper.addTextLimiter(fieldName, 40);
        Helper.addTextLimiter(fieldCpf, 11);
        Helper.addTextLimiter(fieldPhoneNumber, 15);
        Helper.addTextLimiter(fieldEmail, 50);
        Helper.addTextLimiter(fieldAddress, 80);
        Helper.addTextLimiter(fieldNumber, 5);
        Helper.addTextLimiter(fieldCity, 40);
        Helper.addTextLimiter(fieldUf, 2);
    }

    private void register() {
        Client client = getModel();
        if(client != null) {
            ClientDAO dao = new ClientDAO();
            if (dao.insert(client)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldName.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private Client getModel() {
        Client client = null;
        String nome = fieldName.getText();
        String cpf = fieldCpf.getText();
        String telefone = fieldPhoneNumber.getText();
        String email = fieldEmail.getText();
        String endereco = fieldAddress.getText();
        String numero = fieldNumber.getText();
        String cidade = fieldCity.getText();
        String uf = fieldUf.getText();

        if (Validator.validateFields(nome, cpf, telefone, email, endereco, numero, cidade, uf)) {
            client = new Client(nome, cpf, telefone, email, endereco, numero, cidade, uf);
        } else {
            AlertBox.fillAllFields();
        }
        return client;
    }

    private void clearFields() {
        fieldName.clear();
        fieldCpf.clear();
        fieldPhoneNumber.clear();
        fieldEmail.clear();
        fieldAddress.clear();
        fieldNumber.clear();
        fieldCity.clear();
        fieldUf.clear();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}