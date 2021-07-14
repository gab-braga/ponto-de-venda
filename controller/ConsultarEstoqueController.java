package controller;

import dao.EstoqueDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Estoque;
import model.Produto;
import view.ConsultarEstoque;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultarEstoqueController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_search_code_product;

    @FXML
    private TextField field_search_description_product;

    @FXML
    private Button btn_close;

    @FXML
    private TableView<Estoque> table_stock;

    @FXML
    private TableColumn<Estoque, Integer> column_code_product;

    @FXML
    private TableColumn<Estoque, String> column_description_product;

    @FXML
    private TableColumn<Estoque, String> column_type_packed;

    @FXML
    private TableColumn<Estoque, Integer> column_quantity;

    private void fillTable(List<Estoque> estoque) {
        column_code_product.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProduto().getCodigo()).asObject());
        column_description_product.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getDescricao()));
        column_type_packed.setCellValueFactory(new PropertyValueFactory<Estoque, String>("tipoEmbalado"));
        column_quantity.setCellValueFactory(new PropertyValueFactory<Estoque, Integer>("quantidade"));

        ObservableList<Estoque> items = FXCollections.observableArrayList(estoque);
        table_stock.setItems(items);
    }

    private void filter() {
        String code = field_search_code_product.getText();
        String description = field_search_description_product.getText();
        boolean filterByCode = !code.isEmpty();
        boolean filterByDescription = !description.isEmpty();
        if(Helper.validateInteger(code) || !filterByCode) {
            EstoqueDAO estoqueDAO = new EstoqueDAO();
            if(filterByCode && filterByDescription) {
                fillTable(estoqueDAO.queryByCodeOrDescription(Integer.parseInt(code), description));
            }
            else if(filterByCode && !filterByDescription) {
                fillTable(estoqueDAO.queryByCode(Integer.parseInt(code)));
            }
            else if(!filterByCode && filterByDescription) {
                fillTable(estoqueDAO.queryByDescription(description));
            }
            else {
                fillTable(estoqueDAO.queryAllStock());
            }
        }
        else {
            AlertBox.onlyNumbers();
        }
    }

    private void close() {
        ConsultarEstoque.getWindow().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filter();

        field_search_code_product.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_description_product.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_code_product.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_search_code_product.getText().length() >= maxCharacters) event.consume();
        });

        field_search_description_product.setOnKeyTyped(event -> {
            int maxCharacters = 80;
            if(field_search_description_product.getText().length() >= maxCharacters) event.consume();
        });

        btn_close.setOnMouseClicked(click -> {
            close();
        });
    }
}
