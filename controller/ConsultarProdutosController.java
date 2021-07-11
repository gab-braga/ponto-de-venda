package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Produto;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultarProdutosController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_code;

    @FXML
    private TextField field_search_description;

    @FXML
    private TableView<?> table_product;

    @FXML
    private TableColumn<Integer, Produto> column_code;

    @FXML
    private TableColumn<String, Produto> column_description;

    @FXML
    private TableColumn<Double, Produto> column_sale_value;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}