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

public class EditarProdutoController implements Initializable {

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

    private Produto productEdit;

    public void fillFields(Produto produto) {
        this.productEdit = produto;
        fieldCode.setText(Integer.toString(produto.getCodigo()));
        fieldDescription.setText(produto.getDescricao());
        fieldSaleValue.setText(Double.toString(produto.getValorVenda()).replace(".", ","));
        fieldCode.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            edit();
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
                edit();
        });

        Helper.addTextLimiter(fieldCode, 4);
        Helper.addTextLimiter(fieldDescription, 100);
        Helper.addTextLimiter(fieldSaleValue, 8);
    }

    private void edit() {
        Produto produto = getModel();
        if(produto != null) {
            if (ProdutoDAO.update(produto)) {
                AlertBox.editionCompleted();
                closeWindow();
            } else {
                AlertBox.editionError();
            }
        }
    }

    private Produto getModel() {
        Produto produto = null;
        String description = fieldDescription.getText();
        String saleValue = fieldSaleValue.getText().replace(",", ".");

        if (Validator.validateFields(description, saleValue)) {
            if (Validator.validateDouble(saleValue)) {
                produto = new Produto(productEdit.getCodigo(), description, Double.parseDouble(saleValue));
            } else {
                AlertBox.onlyNumbers();
            }
        } else {
            AlertBox.fillAllFields();
        }
        return produto;
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}