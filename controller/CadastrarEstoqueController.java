package controller;

import dao.EstoqueDAO;
import dao.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Estoque;
import model.Produto;
import view.CadastrarEstoque;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CadastrarEstoqueController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_code_product;

    @FXML
    private ComboBox<String> field_packed;

    @FXML
    private TextField field_quantity;

    @FXML
    private Button btn_register;

    @FXML
    private Button btn_cancel;

    private void clearFields() {
        field_code_product.clear();
        field_packed.setValue("");
        field_quantity.clear();
    }

    private boolean validateFields(String codeProduct, String packed, String quantity) {
        return !(codeProduct.isEmpty() || packed.isEmpty() || quantity.isEmpty());
    }

    private void register() {
        String codeProduct = field_code_product.getText();
        String packed = field_packed.getValue();
        String quantity = field_quantity.getText();

        if(validateFields(codeProduct, packed, quantity)) {
            if(Helper.validateInteger(quantity) && Helper.validateInteger(codeProduct)) {
                if(Helper.validateQuantity(Integer.parseInt(quantity))) {
                    List<Produto> produtos = ProdutoDAO.queryByCodeProducts(Integer.parseInt(codeProduct));
                    if(produtos.size() > 0) {
                        if(EstoqueDAO.queryByCode(Integer.parseInt(codeProduct)).size() == 0) {
                            Estoque estoque = new Estoque(produtos.get(0), packed, Integer.parseInt(quantity));
                            if(EstoqueDAO.register(estoque)) {
                                AlertBox.registrationCompleted();
                                clearFields();
                                field_code_product.requestFocus();
                            }
                            else {
                                AlertBox.registrationError();
                            }
                        }
                        else {
                            AlertBox.productAlreadyStocked();
                        }
                    }
                    else {
                        AlertBox.unregisteredProduct();
                    }
                }
                else {
                    AlertBox.invalidQuantityValue();
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

    private void fillFieldPacked() {
        List<String> unidades = new ArrayList<String>();
        unidades.add("UN (Unidade)");
        unidades.add("CX (Caixa)");
        unidades.add("FD (Fardo)");
        unidades.add("PCT (Pacote)");
        unidades.add("KG (Quilograma)");

        ObservableList<String> items = FXCollections.observableArrayList(unidades);
        field_packed.setItems(items);
        field_packed.setValue("");
    }

    private void close() {
        CadastrarEstoque.getWindow().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillFieldPacked();

        btn_register.setOnMouseClicked(click -> {
            register();
        });

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        field_code_product.setOnKeyPressed( keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_packed.requestFocus();
        });

        field_packed.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                field_quantity.requestFocus();
        });

        field_quantity.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        field_code_product.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_code_product.getText().length() >= maxCharacters) event.consume();
        });

        field_quantity.setOnKeyTyped(event -> {
            int maxCharacters = 10;
            if(field_quantity.getText().length() >= maxCharacters) event.consume();
        });
    }
}