package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.SearchGuide;
import com.github.fgabrielbraga.controller.util.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import com.github.fgabrielbraga.model.Product;
import com.github.fgabrielbraga.model.dao.ProductDAO;
import com.github.fgabrielbraga.view.PesquisarProduto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PesquisarProdutoController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldDescriptionProduct;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private ListView<Product> listView;

    private SearchGuide searchGuide;

    public void setDataDriver(SearchGuide searchGuide) {
        this.searchGuide = searchGuide;
    }

    public void setProductDescription(String productDescription) {
        fieldDescriptionProduct.setText(productDescription);
        searchItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchItem();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            selectItemListView();
        });

        fieldDescriptionProduct.setOnKeyReleased(keyEvent -> {
            searchItem();
        });

        listView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                selectItemListView();
        });

        Helper.addTextLimiter(fieldDescriptionProduct, 100);
    }

    private void searchItem() {
        String description = fieldDescriptionProduct.getText();
        ProductDAO dao = new ProductDAO();
        if (description == null || description.isBlank()) {
            fillListView(dao.selectAllProducts());
        } else {
            fillListView(dao.selectProductByDescription(description));
        }
    }

    private void fillListView(List<Product> items) {
        ObservableList groupByProducts = FXCollections.observableArrayList(items);
        listView.setItems(groupByProducts);
    }

    private void selectItemListView() {
        Product product = listView.getSelectionModel().getSelectedItem();
        if (product == null) {
            AlertBox.selectARecord();
        } else {
            this.searchGuide.returnData(product);
            closeWindow();
        }

    }

    private void closeWindow() {
        PesquisarProduto.getWindow().close();
    }
}
