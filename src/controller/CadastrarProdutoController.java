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
import model.dao.ProductDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastrarProdutoController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldCode;

    @FXML
    private TextField fieldDescription;

    @FXML
    private TextField fieldSaleValue;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnSubmit.setOnMouseClicked(click -> {
            register();
        });

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        fieldCode.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldDescription.requestFocus();
        });

        fieldDescription.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldSaleValue.requestFocus();
        });

        fieldSaleValue.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        Helper.addTextLimiter(fieldCode, 40);
        Helper.addTextLimiter(fieldDescription, 100);
        Helper.addTextLimiter(fieldSaleValue, 8);
    }

    private void register() {
        Product product = getModel();
        if(product != null) {
            ProductDAO dao = new ProductDAO();
            if (dao.insert(product)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldCode.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private Product getModel() {
        Product product = null;
        String code = fieldCode.getText();
        String description = fieldDescription.getText();
        String saleValue = fieldSaleValue.getText().replace(",", ".");

        if (Validator.validateFields(code, description, saleValue)) {
            if (Validator.validateInteger(code) && Validator.validateDouble(saleValue)) {
                ProductDAO dao = new ProductDAO();
                if (dao.selectProductByCode(Long.parseLong(code)).size() == 0) {
                    product = new Product(Long.parseLong(code), description, Double.parseDouble(saleValue));
                } else {
                    AlertBox.productAlreadyRegistered();
                }
            } else {
                AlertBox.onlyNumbers();
            }
        } else {
            AlertBox.fillAllFields();
        }
        return product;
    }

    private void clearFields() {
        fieldCode.clear();
        fieldDescription.clear();
        fieldSaleValue.clear();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}