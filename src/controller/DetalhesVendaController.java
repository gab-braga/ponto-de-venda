package controller;

import dao.ItemDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import model.Venda;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetalhesVendaController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btn_cancel;

    @FXML
    private ListView<Item> list_details;

    public void fillListView(Venda venda) {
        List<Item> itemsList = ItemDAO.queryItemsByCodeSale(venda.getCodigo());
        ObservableList items = FXCollections.observableArrayList(itemsList);
        list_details.setItems(items);
        list_details.refresh();
    }

    private void close() {
        ((Stage) root.getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_cancel.setOnMouseClicked(click -> {
            close();
        });
    }
}
