package controller;

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
    private AnchorPane root;

    @FXML
    private TextField field_search_name;

    @FXML
    private ComboBox<String> field_search_permission;

    @FXML
    private Button btn_add_user;

    @FXML
    private Button btn_close;

    @FXML
    private Button btn_search;

    @FXML
    private TableView<Usuario> tableUser;

    @FXML
    private TableColumn<Usuario, String> column_name;

    @FXML
    private TableColumn<Usuario, String> column_permission;

    @FXML
    private MenuItem table_item_refresh;

    @FXML
    private MenuItem table_item_delete;

    private void fillFieldPermission() {
        List<String> permissions = new ArrayList<String>();
        permissions.add("");
        permissions.add(Access.accessUser);
        permissions.add(Access.accessAdmin);
        ObservableList<String> items = FXCollections.observableArrayList(permissions);
        field_search_permission.setItems(items);
        field_search_permission.setValue("");
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    private void fillTable(List<Usuario> usuarios) {
        column_name.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
        column_permission.setCellValueFactory(new PropertyValueFactory<Usuario, String>("permissao"));

        ObservableList<Usuario> items = FXCollections.observableArrayList(usuarios);
        tableUser.setItems(items);
        tableUser.refresh();
    }

    private void filter() {
        String name = field_search_name.getText();
        String permission = field_search_permission.getValue();
        boolean filterByName = !name.isEmpty();
        boolean filterByPermission = !permission.isEmpty();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillFieldPermission();

        filter();

        btn_close.setOnMouseClicked(click -> {
            close();
        });

        btn_search.setOnMouseClicked(click -> {
            filter();
        });

        btn_add_user.setOnMouseClicked(click -> {
            AdicionarUsuario adicionarUsuario = new AdicionarUsuario();
            adicionarUsuario.start(new Stage());
        });

        field_search_name.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        field_search_permission.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)
                filter();
        });

        table_item_refresh.setOnAction(action -> {
            filter();
        });

        table_item_delete.setOnAction(action -> {
            delete();
        });

        Helper.addTextLimiter(field_search_name, 40);
    }
}