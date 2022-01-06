package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.ClienteDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Cliente;

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

    private Cliente clientEdit;

    public void fillFields(Cliente cliente) {
        this.clientEdit = cliente;
        fieldName.setText(cliente.getNome());
        fieldCpf.setText(cliente.getCpf());
        fieldPhoneNumber.setText(cliente.getTelefone());
        fieldEmail.setText(cliente.getEmail());
        field_address.setText(cliente.getEndereco());
        fieldNumber.setText(cliente.getNumero());
        fieldCity.setText(cliente.getCidade());
        fieldUf.setText(cliente.getUf());
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
        Cliente cliente = getModel();
        if(cliente != null) {
            if (ClienteDAO.update(cliente)) {
                AlertBox.editionCompleted();
                closeWindow();
            } else {
                AlertBox.editionError();
            }
        }
    }

    private Cliente getModel() {
        Cliente cliente = null;
        String nome = fieldName.getText();
        String cpf = fieldCpf.getText();
        String telefone = fieldPhoneNumber.getText();
        String email = fieldEmail.getText();
        String endereco = field_address.getText();
        String numero = fieldNumber.getText();
        String cidade = fieldCity.getText();
        String uf = fieldUf.getText();

        if (Validator.validateFields(nome, cpf, telefone, email, endereco, numero, cidade, uf)) {
            cliente = cliente = new Cliente(clientEdit.getCodigo(), nome, cpf, telefone, email, endereco, numero, cidade, uf);
        } else {
            AlertBox.fillAllFields();
        }
        return cliente;
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}