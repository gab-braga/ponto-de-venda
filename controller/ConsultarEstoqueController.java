package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Estoque;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsultarEstoqueController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_code_product;

    @FXML
    private TextField field_search_description_product;

    @FXML
    private TableView<Estoque> table_stock;

    @FXML
    private TableColumn<Integer, Estoque> column_code_product;

    @FXML
    private TableColumn<String, Estoque> column_description_product;

    @FXML
    private TableColumn<String, Estoque> column_type_packed;

    @FXML
    private TableColumn<Integer, Estoque> column_quantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
