package controller;

import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Produto;
import view.PesquisarProduto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PesquisarProdutoController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_description_product;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_select;

    @FXML
    private ListView<Produto> list_products;

    private DataDriver dataDriver;

    public void setDataDriver(DataDriver dataDriver) {
        this.dataDriver = dataDriver;
    }

    public void setProductDescription(String productDescription) {
        field_description_product.setText(productDescription);
        search();
    }

    private void search() {
        String description = field_description_product.getText();
        if (description == null || description.isEmpty()) {
            fillList(ProdutoDAO.queryAllProducts());
        } else {
            fillList(ProdutoDAO.queryByDescriptionProducts(description));
        }
    }

    private void fillList(List<Produto> produtos) {
        ObservableList items = FXCollections.observableArrayList(produtos);
        list_products.setItems(items);
    }

    private void selectProduct() {
        Produto product = list_products.getSelectionModel().getSelectedItem();
        if (product == null) {
            AlertBox.selectARecord();
        } else {
            this.dataDriver.insertAndFillProduct(product);
            close();
        }

    }

    private void close() {
        PesquisarProduto.getWindow().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search();

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_select.setOnMouseClicked(click -> {
            selectProduct();
        });

        field_description_product.setOnKeyReleased(keyEvent -> {
            search();
        });

        list_products.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                selectProduct();
        });

        Helper.addTextLimiter(field_description_product, 100);
    }
}
