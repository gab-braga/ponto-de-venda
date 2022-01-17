package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.dao.UserDAO;
import view.AdicionarUsuario;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField fieldSearchName;

    @FXML
    private ComboBox<String> fieldSearchPermission;

    @FXML
    private Button btnAddUser;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, String> columnName;

    @FXML
    private TableColumn<User, String> columnPermission;

    @FXML
    private MenuItem tableItemRefresh;

    @FXML
    private MenuItem tableItemDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Helper.fillFieldPermission(fieldSearchPermission);

        filter();

        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });

        btnSubmit.setOnMouseClicked(click -> {
            filter();
        });

        btnAddUser.setOnMouseClicked(click -> {
            AdicionarUsuario adicionarUsuario = new AdicionarUsuario();
            adicionarUsuario.start(new Stage());
        });

        fieldSearchName.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        fieldSearchPermission.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        tableItemRefresh.setOnAction(action -> {
            filter();
        });

        tableItemDelete.setOnAction(action -> {
            delete();
        });

        Helper.addTextLimiter(fieldSearchName, 40);
    }

    private void filter() {
        String name = fieldSearchName.getText();
        String permission = fieldSearchPermission.getValue();
        boolean filterByName = !name.isBlank();
        boolean filterByPermission = !permission.isBlank();
        UserDAO dao = new UserDAO();
        if (filterByName && !filterByPermission) {
            fillTable(dao.selectUserByName(name));
        } else if (!filterByName && filterByPermission) {
            fillTable(dao.selectUserByPermission(permission));
        } else if (filterByName && filterByPermission) {
            fillTable(dao.selectUserByNameOrPermission(name, permission));
        } else {
            fillTable(dao.selectAllUsers());
        }
    }

    private void delete() {
        if (AlertBox.confirmationDelete()) {
            User user = tableUser.getSelectionModel().getSelectedItem();
            if (user == null) {
                AlertBox.selectARecord();
            } else {
                UserDAO dao = new UserDAO();
                if (dao.deleteByName(user.getName())) {
                    AlertBox.deleteCompleted();
                    filter();
                } else {
                    AlertBox.deleteError();
                }
            }
        }
    }

    private void fillTable(List<User> users) {
        columnName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        columnPermission.setCellValueFactory(new PropertyValueFactory<User, String>("permission"));

        ObservableList<User> items = FXCollections.observableArrayList(users);
        tableUser.setItems(items);
        tableUser.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}