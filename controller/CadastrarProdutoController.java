package controller;

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

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private void clearFields() {
        field_code.clear();
        field_description.clear();
        field_sale_value.clear();
    }

    private boolean validateFields(String code, String description, String saleVenda) {
        return !(code.isEmpty() || description.isEmpty() || saleVenda.isEmpty());
    }

    private void register() {
        String code = field_code.getText();
        String description = field_description.getText();
        String saleValue = field_sale_value.getText().replace(",", ".");

        if(validateFields(code, description, saleValue)) {
            if(Helper.validateInteger(code) && Helper.validateDouble(saleValue)) {
                if(ProdutoDAO.queryByCodeProducts(Integer.parseInt(code)).size() == 0) {
                    Produto produto = new Produto(Integer.parseInt(code), description, Double.parseDouble(saleValue));
                    if(ProdutoDAO.register(produto)) {
                        AlertBox.registrationCompleted();
                        clearFields();
                        field_code.requestFocus();
                    }
                    else {
                        AlertBox.registrationError();
                    }
                }
                else {
                    AlertBox.productAlreadyRegistered();
                }
            }
            else {
                AlertBox.onlyNumbers();
            }
        }
        else {
            AlertBox.fillAllFields();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_register.setOnMouseClicked(click -> {
            register();
        });

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        field_code.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_description.requestFocus();
        });

        field_description.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_sale_value.requestFocus();
        });

        field_sale_value.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        Helper.addTextLimiter(field_code, 40);
        Helper.addTextLimiter(field_description, 100);
        Helper.addTextLimiter(field_sale_value, 8);
    }
}