package controller;

import dao.EstoqueDAO;
import dao.ProdutoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Estoque;
import model.Produto;
import view.AdicionarEstoque;

import java.net.URL;
import java.util.ResourceBundle;

public class AdicionarEstoqueController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_code_product;

    @FXML
    private TextField field_quantity;

    @FXML
    private TextField field_description;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_add;

    private Estoque stockEdit;

    public void fillFields(Estoque estoque) {
        stockEdit = estoque;
        field_code_product.setText(Integer.toString(estoque.getProduto().getCodigo()));
        field_description.setText(estoque.getProduto().getDescricao());
        field_code_product.setDisable(true);
        field_description.setDisable(true);
    }

    private void close() {
        AdicionarEstoque.getWindow().close();
    }

    private boolean validateFields(String quantity) {
        return !(quantity.isEmpty());
    }

    private void add() {
        String quantity = field_quantity.getText();
        if(Helper.validateInteger(quantity)) {
            if(validateFields(quantity)) {
                if(Helper.validateQuantity(Integer.parseInt(quantity))) {
                    Produto produto = ProdutoDAO.queryByCodeProducts(stockEdit.getProduto().getCodigo()).get(0);
                    Estoque estoque = new Estoque(produto, Integer.parseInt(quantity));
                    if(EstoqueDAO.add(estoque)) {
                        AlertBox.stockUp();
                        close();
                    }
                    else {
                        AlertBox.stockUpError();
                    }
                }
                else {
                    AlertBox.invalidQuantityValue();
                }
            }
            else {
                AlertBox.fillAllFields();
            }
        }
        else {
            AlertBox.onlyNumbers();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_add.setOnMouseClicked(click -> {
            add();
        });

        field_quantity.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                add();
        });

        field_quantity.setOnKeyTyped(event -> {
            int maxCharacters = 10;
            if(field_quantity.getText().length() >= maxCharacters) event.consume();
        });

    }
}
