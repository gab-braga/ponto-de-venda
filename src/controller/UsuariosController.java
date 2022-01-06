package controller;

import controller.util.AlertBox;
import controller.util.Helper;
import dao.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Usuario;
import view.AdicionarUsuario;

import java.net.URL;
import java.util.ArrayList;
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
    private TableView<Usuario> tableUser;

    @FXML
    private TableColumn<Usuario, String> columnName;

    @FXML
    private TableColumn<Usuario, String> columnPermission;

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
        if (filterByName && !filterByPermission) {
            fillTable(UsuarioDAO.queryUserByName(name));
        } else if (!filterByName && filterByPermission) {
            fillTable(UsuarioDAO.queryUserByPermission(permission));
        } else if (filterByName && filterByPermission) {
            fillTable(UsuarioDAO.queryUserByNameOrPermission(name, permission));
        } else {
            fillTable(UsuarioDAO.queryAllUser());
        }
    }

    private void delete() {
        if (AlertBox.confirmationDelete()) {
            Usuario usuario = tableUser.getSelectionModel().getSelectedItem();
            if (usuario == null) {
                AlertBox.selectARecord();
            } else {
                if (UsuarioDAO.deleteByName(usuario.getNome())) {
                    AlertBox.deleteCompleted();
                    filter();
                } else {
                    AlertBox.deleteError();
                }
            }
        }
    }

    private void fillTable(List<Usuario> usuarios) {
        columnName.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
        columnPermission.setCellValueFactory(new PropertyValueFactory<Usuario, String>("permissao"));

        ObservableList<Usuario> items = FXCollections.observableArrayList(usuarios);
        tableUser.setItems(items);
        tableUser.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}