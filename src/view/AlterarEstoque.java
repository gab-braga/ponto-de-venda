package view;

import controller.AlterarEstoqueController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Stock;

import java.io.IOException;

public class AlterarEstoque extends Application {

    private Stock stockEdit;

    public AlterarEstoque(Stock stockEdit) {
        this.stockEdit = stockEdit;
    }

    private final String title = "Alterar Stock";

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
            FXMLLoader file = new FXMLLoader(getClass().getResource("/view/fxml/alterar_estoque.fxml"));
            Parent root = file.load();

            AlterarEstoqueController alterarEstoqueController = (AlterarEstoqueController) file.getController();
            alterarEstoqueController.fillFields(stockEdit);

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
