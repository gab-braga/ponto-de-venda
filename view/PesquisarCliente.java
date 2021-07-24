package view;

import controller.CaixaController;
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

    private CaixaController caixaController;
    private String clientName;

    public PesquisarCliente(CaixaController caixaController, String clientName) {
        this.caixaController = caixaController;
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/pesquisar_cliente.fxml"));
            Parent root = fxmlLoader.load();
            PesquisarClienteController pesquisarClienteController = fxmlLoader.getController();
            pesquisarClienteController.setCaixaController(this.caixaController);
            pesquisarClienteController.setClientName(clientName);
            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.NONE);
            stage.initOwner(Caixa.getWindow());
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException ex) {
            System.err.println(String.format("ERRO (%s): ", title));
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
