package com.github.fgabrielbraga.view;

import com.github.fgabrielbraga.controller.DetalhesVendaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.github.fgabrielbraga.model.Sale;

import java.io.IOException;

public class DetalhesVenda extends Application {

    private Sale sale;

    public DetalhesVenda(Sale sale) {
        this.sale = sale;
    }

    private final String title = "Detalhes da Sale";

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
            FXMLLoader file = new FXMLLoader(getClass().getResource("/com/github/fgabrielbraga/view/fxml/detalhes_venda.fxml"));
            Parent root = file.load();

            DetalhesVendaController detalhesVendaController = (DetalhesVendaController) file.getController();
            detalhesVendaController.fillListView(sale);

            Scene scene = new Scene(root);

            setWindow(stage);
            stage.setScene(scene);
            stage.initStyle(StageStyle.DECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(ConsultarVendas.getWindow());
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
