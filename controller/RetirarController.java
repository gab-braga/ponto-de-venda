package controller;

import dao.CaixaDAO;
import dao.SaidaDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import model.Caixa;
import model.Saida;
import view.Retirar;

import java.net.URL;
import java.util.ResourceBundle;

public class RetirarController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField field_operator;

    @FXML
    private TextField field_value_exit;

    @FXML
    private TextField field_reason;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_remove;

    private void clerFields() {
        field_value_exit.clear();
        field_reason.clear();
    }

    private boolean validateFields(String operator, String value, String reason) {
        return !(operator.isEmpty() || value.isEmpty() || reason.isEmpty());
    }

    private void remove() {
        String operator = field_operator.getText();
        String value = field_value_exit.getText().replace(",", ".");
        String reason = field_reason.getText();

        if(validateFields(operator, value, reason)) {
            if(Helper.validateDouble(value)) {
                Saida saida = new Saida(Double.parseDouble(value), Helper.getCurrentDate(), reason, operator);
                Caixa caixa = new Caixa(Double.parseDouble(value), Helper.getCurrentDate(), 2);
                if(SaidaDAO.register(saida) && CaixaDAO.register(caixa)) {
                    AlertBox.operationCompleted();
                    clerFields();
                    field_value_exit.requestFocus();
                }
                else {
                    AlertBox.operationError();
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

    private void insertOperator() {
        field_operator.setText(Acesso.getUser());
        field_operator.setDisable(true);
    }

    private void close() {
        Retirar.getWindow().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insertOperator();

        btn_remove.setOnMouseClicked(click -> {
            remove();
        });

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        field_operator.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_value_exit.requestFocus();
        });

        field_value_exit.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                field_reason.requestFocus();
        });

        field_reason.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                remove();
        });

        field_operator.setOnKeyTyped(event -> {
            int maxCharacters = 40;
            if(field_operator.getText().length() >= maxCharacters)
                event.consume();
        });

        field_value_exit.setOnKeyTyped(event -> {
            int maxCharacters = 10;
            if(field_value_exit.getText().length() >= maxCharacters)
                event.consume();
        });

        field_reason.setOnKeyTyped(event -> {
            int maxCharacters = 100;
            if(field_reason.getText().length() >= maxCharacters)
                event.consume();
        });
    }
}