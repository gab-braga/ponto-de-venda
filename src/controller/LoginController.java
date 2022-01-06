package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;
import view.MenuPrincipal;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldLoginUser;

    @FXML
    private PasswordField fieldLoginPassword;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fieldLoginUser.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldLoginPassword.requestFocus();
        });

        fieldLoginPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                enter();
        });

        btnSubmit.setOnMouseClicked(click -> {
            enter();
        });

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        Helper.addTextLimiter(fieldLoginUser, 40);
        Helper.addTextLimiter(fieldLoginPassword, 20);
    }

    private void enter() {
        String username = fieldLoginUser.getText();
        String password = fieldLoginPassword.getText();
        if (Validator.validateFields(username, password)) {
            List<Usuario> usuarios = UsuarioDAO.queryUserPassword(username, password);
            if (!usuarios.isEmpty() || usuarios == null) {
                Usuario usuario = usuarios.get(0);
                Access.checkFullAccess(usuario.getPermissao());
                Access.setOperator(usuario);
                closeWindow();
                (new MenuPrincipal()).start(new Stage());
            } else {
                AlertBox.incorrectUserOrPassword();
            }
        } else {
            AlertBox.fillAllFields();
        }
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
