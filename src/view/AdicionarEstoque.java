package view;

import controller.AdicionarEstoqueController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Estoque;

import java.io.IOException;
import java.net.URISyntaxException;

public class AdicionarEstoque extends Application {

    private Estoque stockEdit;

    public AdicionarEstoque(Estoque stockEdit) {
        this.stockEdit = stockEdit;
    }

    private final String title = "Adicionar Estoque";

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
            FXMLLoader file = new FXMLLoader(getClass().getResource("/view/fxml/adicionar_estoque.fxml"));
            Parent root = file.load();

            AdicionarEstoqueController adicionarEstoqueController = (AdicionarEstoqueController) file.getController();
            adicionarEstoqueController.fillFields(stockEdit);

            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(ConsultarEstoque.getWindow());
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
