package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdicionarUsuarioController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private ComboBox<String> fieldPermission;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillFieldPermission();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            register();
        });

        fieldName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldPassword.requestFocus();
        });

        fieldPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldPermission.requestFocus();
        });

        fieldPermission.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        Helper.addTextLimiter(fieldName, 40);
        Helper.addTextLimiter(fieldPassword, 20);
    }

    private void fillFieldPermission() {
        List<String> permissions = new ArrayList<String>();
        permissions.add("");
        permissions.add(Access.BASIC_ACCESS);
        permissions.add(Access.ADMINISTRATIVE_ACCESS);
        ObservableList<String> items = FXCollections.observableArrayList(permissions);
        fieldPermission.setItems(items);
        fieldPermission.setValue("");
    }

    private void register() {
        Usuario usuario = getModel();
        if(usuario != null) {
            if (UsuarioDAO.register(usuario)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldName.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private Usuario getModel() {
        Usuario usuario = null;
        String name = fieldName.getText();
        String password = fieldPassword.getText();
        String permission = fieldPermission.getValue();
        if (Validator.validateFields(name, password, permission)) {
            if (UsuarioDAO.queryUserByName(name).size() == 0) {
                usuario = new Usuario(name, password, permission);
            } else {
                AlertBox.userAlreadyRegistered();
            }
        } else {
            AlertBox.fillAllFields();
        }
        return usuario;
    }

    private void clearFields() {
        fieldName.clear();
        fieldPassword.clear();
        fieldPermission.setValue("");
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}