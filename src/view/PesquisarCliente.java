package view;

import controller.util.SearchGuide;
import controller.PesquisarClienteController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PesquisarCliente extends Application {

    private SearchGuide searchGuide;
    private String clientName;

    public PesquisarCliente(SearchGuide searchGuide, String clientName) {
        this.searchGuide = searchGuide;
        this.clientName = clientName;
    }

    private final String title = "Pesquisar Cliente";

    private static Stage window;

    private void setWindow(Stage window) {
        this.window = window;
    }

    public static Stage getWindow() {
        return window;
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/pesquisar_cliente.fxml"));
            Parent root = fxmlLoader.load();
            PesquisarClienteController pesquisarClienteController = fxmlLoader.getController();
            pesquisarClienteController.setSearchGuide(this.searchGuide);
            pesquisarClienteController.setClientName(clientName);
            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(MenuPrincipal.getWindow());
            stage.setTitle(title);
            stage.setResizable(false);
            stage.getIcons().add(new Icon().getImage());
            stage.show();
        } catch (IOException ex) {
            System.err.println(String.format("ERRO (%s)", title));
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
