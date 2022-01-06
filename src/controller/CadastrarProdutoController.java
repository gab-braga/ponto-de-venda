package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Produto;

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
        Produto produto = getModel();
        if(produto != null) {
            if (ProdutoDAO.register(produto)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldCode.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private Produto getModel() {
        Produto produto = null;
        String code = fieldCode.getText();
        String description = fieldDescription.getText();
        String saleValue = fieldSaleValue.getText().replace(",", ".");

        if (Validator.validateFields(code, description, saleValue)) {
            if (Validator.validateInteger(code) && Validator.validateDouble(saleValue)) {
                if (ProdutoDAO.queryProductByCode(Integer.parseInt(code)).size() == 0) {
                    produto = new Produto(Integer.parseInt(code), description, Double.parseDouble(saleValue));
                } else {
                    AlertBox.productAlreadyRegistered();
                }
            } else {
                AlertBox.onlyNumbers();
            }
        } else {
            AlertBox.fillAllFields();
        }
        return produto;
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