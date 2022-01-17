package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.SearchGuide;
import com.github.fgabrielbraga.controller.util.Helper;
import com.github.fgabrielbraga.controller.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.github.fgabrielbraga.model.Stock;
import com.github.fgabrielbraga.model.Product;
import com.github.fgabrielbraga.model.dao.ProductDAO;
import com.github.fgabrielbraga.model.dao.StockDAO;
import com.github.fgabrielbraga.view.PesquisarProduto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CadastrarEstoqueController implements Initializable, SearchGuide {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldCodeProduct;

    @FXML
    private ComboBox<String> fieldUnity;

    @FXML
    private TextField fieldQuantity;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @Override
    public void returnData(Object o) {
        if (Validator.validateObject(o)) {
            fieldCodeProduct.setText(Long.toString(((Product) o).getCode()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helper.fillFieldUnity(fieldUnity);

        btnSubmit.setOnMouseClicked(click -> {
            register();
        });

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        fieldCodeProduct.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldUnity.requestFocus();
        });

        fieldUnity.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldQuantity.requestFocus();
        });

        fieldQuantity.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        btnSearch.setOnMouseClicked(click -> {
            openSearchWindow();
        });

        Helper.addTextLimiter(fieldCodeProduct, 40);
        Helper.addTextLimiter(fieldQuantity, 6);
    }

    private void register() {
        Stock stock = getModel();
        if(stock != null) {
            StockDAO dao = new StockDAO();
            if (dao.insert(stock)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldCodeProduct.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private Stock getModel() {
        Stock stockModel = null;
        String codeProduct = fieldCodeProduct.getText();
        String packed = fieldUnity.getValue();
        String quantity = fieldQuantity.getText();

        if (Validator.validateFields(codeProduct, packed, quantity)) {
            if (Validator.validateInteger(quantity) && Validator.validateInteger(codeProduct)) {
                if (Validator.validateQuantity(Integer.parseInt(quantity))) {
                    ProductDAO productDAO = new ProductDAO();
                    List<Product> products = productDAO.selectProductByCode(Long.parseLong(codeProduct));
                    if (products.size() > 0) {
                        Product product = products.get(0);
                        StockDAO dao = new StockDAO();
                        List<Stock> stock = dao.selectStockByProductCode(product);
                        if (stock.size() == 0) {
                            stockModel = new Stock(product, packed, Integer.parseInt(quantity));
                        } else {
                            AlertBox.productAlreadyStocked();
                        }
                    } else {
                        AlertBox.unregisteredProduct();
                    }
                } else {
                    AlertBox.invalidQuantityValue();
                }
            } else {
                AlertBox.onlyNumbers();
            }
        } else {
            AlertBox.fillAllFields();
        }
        return stockModel;
    }

    private void clearFields() {
        fieldCodeProduct.clear();
        fieldUnity.setValue("");
        fieldQuantity.clear();
    }

    private void openSearchWindow() {
        PesquisarProduto pesquisarProduto = new PesquisarProduto(this, "");
        pesquisarProduto.start(new Stage());
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}