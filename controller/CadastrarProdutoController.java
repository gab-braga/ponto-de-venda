package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastrarProdutoController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_code;

    @FXML
    private TextField field_description;

    @FXML
    private TextField field_sale_value;

    @FXML
    private Button btn_register;

    @FXML
    private Button btn_cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}