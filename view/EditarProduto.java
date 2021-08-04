package view;

import controller.EditarProdutoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Produto;

import java.io.IOException;

public class EditarProduto extends Application {

    private Produto produtoEdit;

    public EditarProduto(Produto produtoEdit) {
        this.produtoEdit = produtoEdit;
    }

    private final String title = "Editar Produto";

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/editar_produto.fxml"));
            Parent root = fxmlLoader.load();

            EditarProdutoController editarProdutoController = (EditarProdutoController) fxmlLoader.getController();
            editarProdutoController.fillFields(produtoEdit);

            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(ConsultarProdutos.getWindow());
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
