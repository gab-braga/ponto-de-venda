package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Item;
import model.Sale;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetalhesVendaController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnCancel;

    @FXML
    private ListView<Item> listDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCancel.setOnMouseClicked(click -> {
            closeWindow();
        });
    }

    public void fillListView(Sale sale) {
        ObservableList items = FXCollections.observableArrayList(sale.getItems());
        listDetails.setItems(items);
        listDetails.refresh();
    }

    private void closeWindow() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }
}
