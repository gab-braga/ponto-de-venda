package controller;

import dao.ClienteDAO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

        Cliente cliente = null;
        if(validateFields(nome, cpf, telefone, email, endereco, numero, cidade, uf)) {
            cliente = new Cliente(nome, cpf, telefone, email, endereco, numero, cidade, uf);
            ClienteDAO clienteDAO =  new ClienteDAO();
            if (clienteDAO.register(cliente)) {
                AlertBox.registrationCompleted();
                clearFields();
            }
            else {
                AlertBox.registrationError();
            }
        }
        else {
            AlertBox.fillAllFields();
        }
    }

    private void close() {
        ((Stage) this.root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                register();
            }
        });

        btn_cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close();
            }
        });
    }
}