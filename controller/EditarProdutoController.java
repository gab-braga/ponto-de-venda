package controller;

import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Produto;
import view.EditarProduto;

import java.net.URL;
import java.util.ResourceBundle;

public class EditarProdutoController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_code;

    @FXML
    private TextField field_description;

    @FXML
    private TextField field_sale_value;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_edit;

    private Produto productEdit;

    public void fillFields(Produto produto) {
        this.productEdit = produto;
        field_code.setText(Integer.toString(produto.getCodigo()));
        field_description.setText(produto.getDescricao());
        field_sale_value.setText(Double.toString(produto.getValorVenda()).replace(".", ","));
        field_code.setDisable(true);
    }

    private boolean validateFields(String description, String saleValue) {
        return !(description.isEmpty() || saleValue.isEmpty());
    }

    private void edit() {
        String description = field_description.getText();
        String saleValue = field_sale_value.getText().replace(",", ".");

        if(validateFields(description, saleValue)) {
            if(Helper.validateDouble(saleValue)) {
                Produto produto = new Produto(productEdit.getCodigo(), description, Double.parseDouble(saleValue));
                if(ProdutoDAO.update(produto)) {
                    AlertBox.editionCompleted();
                    close();
                }
                else {
                    AlertBox.editionError();
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

    private void close() {
        EditarProduto.getWindow().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_edit.setOnMouseClicked(click -> {
            edit();
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
                edit();
        });

        field_code.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_code.getText().length() >= maxCharacters) event.consume();
        });

        field_description.setOnKeyTyped(event -> {
            int maxCharacters = 100;
            if(field_description.getText().length() >= maxCharacters) event.consume();
        });

        field_sale_value.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_sale_value.getText().length() >= maxCharacters) event.consume();
        });
    }
}