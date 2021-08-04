package view;

import controller.EditarClienteController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Cliente;

import java.io.IOException;

public class EditarCliente extends Application {

    private Cliente clientEdit;

    public EditarCliente(Cliente clientEdit) {
        this.clientEdit = clientEdit;
    }

    private final String title = "Editar Cliente";

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
            FXMLLoader file = new FXMLLoader(getClass().getResource("fxml/editar_cliente.fxml"));
            Parent root = file.load();

            EditarClienteController editarClienteController = (EditarClienteController) file.getController();
            editarClienteController.fillFields(clientEdit);

            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(ConsultarClientes.getWindow());
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println(String.format("ERRO (%s): ", title));
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
