package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastrarEstoqueController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label field_quantity;

    @FXML
    private TextField field_code_product;

    @FXML
    private ComboBox<String> field_packed;

    @FXML
    private Button btn_register;

    @FXML
    private Button btn_cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}