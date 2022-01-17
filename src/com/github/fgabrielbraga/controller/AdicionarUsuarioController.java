package com.github.fgabrielbraga.controller;

import com.github.fgabrielbraga.controller.util.AlertBox;
import com.github.fgabrielbraga.controller.util.Helper;
import com.github.fgabrielbraga.controller.util.Validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.github.fgabrielbraga.model.User;
import com.github.fgabrielbraga.model.dao.UserDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdicionarUsuarioController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldPassword;

    @FXML
    private ComboBox<String> fieldPermission;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillFieldPermission();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            register();
        });

        fieldName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldPassword.requestFocus();
        });

        fieldPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                fieldPermission.requestFocus();
        });

        fieldPermission.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                register();
        });

        Helper.addTextLimiter(fieldName, 40);
        Helper.addTextLimiter(fieldPassword, 20);
    }

    private void fillFieldPermission() {
        List<String> permissions = new ArrayList<String>();
        permissions.add("");
        permissions.add(Access.BASIC_ACCESS);
        permissions.add(Access.ADMINISTRATIVE_ACCESS);
        ObservableList<String> items = FXCollections.observableArrayList(permissions);
        fieldPermission.setItems(items);
        fieldPermission.setValue("");
    }

    private void register() {
        User user = getModel();
        if(user != null) {
            UserDAO dao = new UserDAO();
            if (dao.insert(user)) {
                AlertBox.registrationCompleted();
                clearFields();
                fieldName.requestFocus();
            } else {
                AlertBox.registrationError();
            }
        }
    }

    private User getModel() {
        User user = null;
        String name = fieldName.getText();
        String password = fieldPassword.getText();
        String permission = fieldPermission.getValue();
        if (Validator.validateFields(name, password, permission)) {
            UserDAO dao = new UserDAO();
            if (dao.selectUserByName(name).size() == 0) {
                user = new User(name, password, permission);
            } else {
                AlertBox.userAlreadyRegistered();
            }
        } else {
            AlertBox.fillAllFields();
        }
        return user;
    }

    private void clearFields() {
        fieldName.clear();
        fieldPassword.clear();
        fieldPermission.setValue("");
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}