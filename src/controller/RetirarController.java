package controller;

import dao.CaixaDAO;
import dao.SaidaDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Caixa;
import model.Saida;

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

    private void insertOperator() {
        field_operator.setText(Access.getUser().getNome());
        field_operator.setDisable(true);
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

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

        if (validateFields(operator, value, reason)) {
            if (Helper.validateDouble(value)) {
                Caixa caixa = new Caixa(0.0, Double.parseDouble(value), Helper.getCurrentDate());
                if (CaixaDAO.register(caixa)) {
                    Saida saida = new Saida(Double.parseDouble(value), Helper.getCurrentDate(), reason, caixa, Access.getUser());
                    if (SaidaDAO.register(saida)) {
                        AlertBox.operationCompleted();
                        clerFields();
                        field_value_exit.requestFocus();
                    }
                } else {
                    AlertBox.operationError();
                }
            } else {
                AlertBox.onlyNumbers();
            }
        } else {
            AlertBox.fillAllFields();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insertOperator();

        btn_cancel.setOnMouseClicked(click -> {
            close();
        });

        btn_remove.setOnMouseClicked(click -> {
            remove();
        });

        field_operator.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                field_value_exit.requestFocus();
        });

        field_value_exit.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                field_reason.requestFocus();
        });

        field_reason.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                remove();
        });

        Helper.addTextLimiter(field_operator, 40);
        Helper.addTextLimiter(field_value_exit, 8);
        Helper.addTextLimiter(field_reason, 100);
    }
}