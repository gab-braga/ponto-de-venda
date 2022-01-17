package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Product;
import model.Stock;
import model.dao.ProductDAO;
import model.dao.StockDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class AlterarEstoqueController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldCodeProduct;

    @FXML
    private TextField fieldQuantity;

    @FXML
    private TextField fieldDescription;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    private Stock stockEdit;

    public void fillFields(Stock stock) {
        stockEdit = stock;
        fieldCodeProduct.setText(Long.toString(stock.getProduto().getCode()));
        fieldDescription.setText(stock.getProduto().getDescription());
        fieldQuantity.setText(stock.getQuantity().toString());
        fieldCodeProduct.setDisable(true);
        fieldDescription.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            alter();
        });

        fieldQuantity.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                alter();
        });

        Helper.addTextLimiter(fieldQuantity, 10);
    }

    private void alter() {
        Stock stock = getModel();
        if(stock != null) {
            StockDAO dao = new StockDAO();
            if (dao.adjustStock(stock)) {
                AlertBox.stockUp();
                closeWindow();
            } else {
                AlertBox.stockUpError();
            }
        }
    }

    private Stock getModel() {
        Stock stock = null;
        String quantity = fieldQuantity.getText();
        if (Validator.validateInteger(quantity)) {
            if (Validator.validateFields(quantity)) {
                if (Validator.validateQuantity(Integer.parseInt(quantity))) {
                    ProductDAO dao = new ProductDAO();
                    Product product = dao.selectProductByCode(stockEdit.getProduto().getCode()).get(0);
                    stock = new Stock(product, Integer.parseInt(quantity));
                    stock.setCode(stockEdit.getCode());
                } else {
                    AlertBox.invalidQuantityValue();
                }
            } else {
                AlertBox.fillAllFields();
            }
        } else {
            AlertBox.onlyNumbers();
        }
        return stock;
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
