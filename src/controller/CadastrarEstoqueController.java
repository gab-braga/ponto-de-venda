package controller;

import controller.util.AlertBox;
import controller.util.SearchGuide;
import controller.util.Helper;
import controller.util.Validator;
import dao.EstoqueDAO;
import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Estoque;
import model.Produto;
import view.PesquisarProduto;

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
    public void searchAndFillData(Object o) {
        if (Validator.validateObject(o)) {
            fieldCodeProduct.setText(Integer.toString(((Produto) o).getCodigo()));
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
        Estoque estoque = getModel();
        if(estoque != null) {
            if (EstoqueDAO.register(estoque)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldCodeProduct.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private Estoque getModel() {
        Estoque estoque = null;
        String codeProduct = fieldCodeProduct.getText();
        String packed = fieldUnity.getValue();
        String quantity = fieldQuantity.getText();

        if (Validator.validateFields(codeProduct, packed, quantity)) {
            if (Validator.validateInteger(quantity) && Validator.validateInteger(codeProduct)) {
                if (Validator.validateQuantity(Integer.parseInt(quantity))) {
                    List<Produto> produtos = ProdutoDAO.queryProductByCode(Integer.parseInt(codeProduct));
                    if (produtos.size() > 0) {
                        if (EstoqueDAO.queryStockByCode(Integer.parseInt(codeProduct)).size() == 0) {
                            estoque = new Estoque(produtos.get(0), packed, Integer.parseInt(quantity));
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
        return estoque;
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