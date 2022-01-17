package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.Helper;
import com.github.fgabrielbraga.controller.util.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.github.fgabrielbraga.model.Acquisition;
import com.github.fgabrielbraga.model.dao.AcquisitionDAO;

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
        fieldOperator.setText(Access.getOperator().getName());
        fieldOperator.setDisable(true);
    }

    private void remove() {
        String operator = fieldOperator.getText();
        String value = fieldValueExit.getText().replace(",", ".");
        String reason = fieldReason.getText();

        if (Validator.validateFields(operator, value, reason)) {
            if (Validator.validateDouble(value)) {
                Acquisition acquisition = new Acquisition(Double.parseDouble(value), Access.getOperator(), Helper.getCurrentDate(), reason);
                AcquisitionDAO dao = new AcquisitionDAO();
                if (dao.insert(acquisition)) {
                    AlertBox.operationCompleted();
                    clerFields();
                    fieldValueExit.requestFocus();
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