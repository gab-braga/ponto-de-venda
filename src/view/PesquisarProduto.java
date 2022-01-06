package view;

import controller.util.SearchGuide;
import controller.PesquisarProdutoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PesquisarProduto extends Application {

    private SearchGuide searchGuide;
    private String productDescription;

    public PesquisarProduto(SearchGuide searchGuide, String productDescription) {
        this.searchGuide = searchGuide;
        this.productDescription = productDescription;
    }

    private final String title = "Pesquisar Produto";

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fxml/pesquisar_produto.fxml"));
            Parent root = fxmlLoader.load();
            PesquisarProdutoController pesquisarProdutoController = fxmlLoader.getController();
            pesquisarProdutoController.setDataDriver(this.searchGuide);
            pesquisarProdutoController.setProductDescription(productDescription);
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
