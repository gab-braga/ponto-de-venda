package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ConsultarClientes extends Application {
    private final String title = "Consultar Clientes";

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
            Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/consultar_clientes.fxml"));
            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.NONE);
            stage.initOwner(MenuPrincipal.getWindow());
            stage.setTitle(title);
            stage.setResizable(false);
            stage.getIcons().add(new Image("/view/images/logo-ponto-de-venda.png"));
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