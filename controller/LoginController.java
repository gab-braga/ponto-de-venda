package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
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
        ((Stage) this.root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_cancel.setOnMouseClicked(click -> {
            close();
        });
    }
}
