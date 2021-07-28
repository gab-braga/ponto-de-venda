package controller;

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

public class CadastrarClienteController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_cpf;

    @FXML
    private TextField field_phone;

    @FXML
    private TextField field_email;

    @FXML
    private TextField field_address;

    @FXML
    private TextField field_number;

    @FXML
    private TextField field_city;

    @FXML
    private TextField field_uf;

    @FXML
    private Button btn_register;

    @FXML
    private Button btn_cancel;

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private void clearFields() {
        field_name.clear();
        field_cpf.clear();
        field_phone.clear();
        field_email.clear();
        field_address.clear();
        field_number.clear();
        field_city.clear();
        field_uf.clear();
    }

    private boolean validateFields(String nome, String cpf, String telefone, String email, String endereco, String numero, String cidade, String uf) {
        return !(nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || endereco.isEmpty() || numero.isEmpty() || cidade.isEmpty() || uf.isEmpty());
    }

    private void register() {
        String nome = field_name.getText();
        String cpf = field_cpf.getText();
        String telefone = field_phone.getText();
        String email = field_email.getText();
        String endereco = field_address.getText();
        String numero = field_number.getText();
        String cidade = field_city.getText();
        String uf = field_uf.getText();


        if(validateFields(nome, cpf, telefone, email, endereco, numero, cidade, uf)) {
            Cliente cliente =  cliente = new Cliente(nome, cpf, telefone, email, endereco, numero, cidade, uf);
            if(ClienteDAO.register(cliente)) {
                AlertBox.registrationCompleted();
                clearFields();
                field_name.requestFocus();
            }
            else {
                AlertBox.registrationError();
            }
        }
        else {
            AlertBox.fillAllFields();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_register.setOnMouseClicked(click -> {
            register();
        });

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        field_name.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_cpf.requestFocus();
        } );

        field_cpf.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_phone.requestFocus();
        } );

        field_phone.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_email.requestFocus();
        } );

        field_email.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_address.requestFocus();
        } );

        field_address.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_number.requestFocus();
        } );

        field_number.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_city.requestFocus();
        } );

        field_city.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_uf.requestFocus();
        } );

        field_uf.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                register();
        } );

        Helper.addTextLimiter(field_name, 40);
        Helper.addTextLimiter(field_cpf, 11);
        Helper.addTextLimiter(field_phone, 15);
        Helper.addTextLimiter(field_email, 50);
        Helper.addTextLimiter(field_address, 80);
        Helper.addTextLimiter(field_number, 5);
        Helper.addTextLimiter(field_city, 40);
        Helper.addTextLimiter(field_uf, 2);
    }
}