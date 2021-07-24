package controller;

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
    private AnchorPane root;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_password;

    @FXML
    private ComboBox<String> field_permission;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_add;

    private void fillFieldPermission() {
        List<String> permissions = new ArrayList<String>();
        permissions.add("");
        permissions.add(Access.accessUser);
        permissions.add(Access.accessAdmin);
        ObservableList<String> items = FXCollections.observableArrayList(permissions);
        field_permission.setItems(items);
        field_permission.setValue("");
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private void clearFields() {
        field_name.clear();
        field_password.clear();
        field_permission.setValue("");
    }

    private boolean validateFields(String name, String password, String permission) {
        return !(name.isEmpty() || password.isEmpty() || permission.isEmpty());
    }

    private void register() {
        String name = field_name.getText();
        String password = field_password.getText();
        String permission = field_permission.getValue();
        if(validateFields(name, password, permission)) {
            if(UsuarioDAO.queryUserByName(name).size() == 0) {
                Usuario usuario = new Usuario(name, password, permission);
                if(UsuarioDAO.register(usuario)) {
                    AlertBox.registrationCompleted();
                    clearFields();
                    field_name.requestFocus();
                }
                else {
                    AlertBox.registrationError();
                }
            }
            else {
                AlertBox.userAlreadyRegistered();
            }
        }
        else {
            AlertBox.fillAllFields();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillFieldPermission();

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_add.setOnMouseClicked(click -> {
            register();
        });

        field_name.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_password.requestFocus();
        });

        field_password.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_permission.requestFocus();
        });

        field_permission.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        Helper.addTextLimiter(field_name, 40);
        Helper.addTextLimiter(field_password, 20);
    }
}