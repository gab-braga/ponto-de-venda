package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
import dao.EstoqueDAO;
import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Estoque;
import model.Produto;

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

    private Estoque stockEdit;

    public void fillFields(Estoque estoque) {
        stockEdit = estoque;
        fieldCodeProduct.setText(Integer.toString(estoque.getProduto().getCodigo()));
        fieldDescription.setText(estoque.getProduto().getDescricao());
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
        Estoque estoque = getModel();
        if(estoque != null) {
            if (EstoqueDAO.add(estoque)) {
                AlertBox.stockUp();
                closeWindow();
            } else {
                AlertBox.stockUpError();
            }
        }
    }

    private Estoque getModel() {
        Estoque estoque = null;
        String quantity = fieldQuantity.getText();
        if (Validator.validateInteger(quantity)) {
            if (Validator.validateFields(quantity)) {
                if (Validator.validateQuantity(Integer.parseInt(quantity))) {
                    Produto produto = ProdutoDAO.queryProductByCode(stockEdit.getProduto().getCodigo()).get(0);
                    estoque = new Estoque(produto, Integer.parseInt(quantity));
                } else {
                    AlertBox.invalidQuantityValue();
                }
            } else {
                AlertBox.fillAllFields();
            }
        } else {
            AlertBox.onlyNumbers();
        }
        return estoque;
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
