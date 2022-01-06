package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import controller.util.Validator;
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
    private AnchorPane rootPane;

    @FXML
    private TextField fieldOperator;

    @FXML
    private TextField fieldValueExit;

    @FXML
    private TextField fieldReason;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insertOperator();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            remove();
        });

        fieldOperator.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldValueExit.requestFocus();
        });

        fieldValueExit.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldReason.requestFocus();
        });

        fieldReason.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                remove();
        });

        Helper.addTextLimiter(fieldOperator, 40);
        Helper.addTextLimiter(fieldValueExit, 8);
        Helper.addTextLimiter(fieldReason, 100);
    }

    private void insertOperator() {
        fieldOperator.setText(Access.getOperator().getNome());
        fieldOperator.setDisable(true);
    }

    private void remove() {
        String operator = fieldOperator.getText();
        String value = fieldValueExit.getText().replace(",", ".");
        String reason = fieldReason.getText();

        if (Validator.validateFields(operator, value, reason)) {
            if (Validator.validateDouble(value)) {
                Caixa caixa = new Caixa(0.0, Double.parseDouble(value), Helper.getCurrentDate());
                if (CaixaDAO.register(caixa)) {
                    Saida saida = new Saida(Double.parseDouble(value), Helper.getCurrentDate(), reason, caixa, Access.getOperator());
                    if (SaidaDAO.register(saida)) {
                        AlertBox.operationCompleted();
                        clerFields();
                        fieldValueExit.requestFocus();
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

    private void clerFields() {
        fieldValueExit.clear();
        fieldReason.clear();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}