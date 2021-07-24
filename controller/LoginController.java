package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;
import view.Login;
import view.MenuPrincipal;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField login_user;

    @FXML
    private JFXPasswordField login_password;

    @FXML
    private JFXButton btn_submit;

    @FXML
    private JFXButton btn_cancel;

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private boolean validateFields(String usuario, String senha) {
        return !(usuario.isEmpty() || senha.isEmpty());
    }

    private void enter() {
        String username = login_user.getText();
        String password = login_password.getText();
        if(validateFields(username, password)) {
            List<Usuario> usuarios = UsuarioDAO.queryUserPassword(username, password);
            if(!usuarios.isEmpty()) {
                Usuario usuario = usuarios.get(0);
                Acesso.checkFullAccess(usuario.getPermissao());
                Acesso.setUser(usuario.getNome());
                close();
                (new MenuPrincipal()).start(new Stage());
            }
            else {
                AlertBox.incorrectUserOrPassword();
            }
        }
        else {
            AlertBox.fillAllFields();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        login_user.setOnKeyPressed( keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                login_password.requestFocus();
        } );

        login_password.setOnKeyPressed( keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                enter();
        } );

        btn_submit.setOnMouseClicked(click -> {
            enter();
        });

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        Helper.addTextLimiter(login_user, 40);
        Helper.addTextLimiter(login_password, 20);
    }
}
