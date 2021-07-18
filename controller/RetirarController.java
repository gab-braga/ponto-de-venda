package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RetirarController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_operator;

    @FXML
    private TextField field_value_exit;

    @FXML
    private TextField field_reason;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_remove;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}