package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URISyntaxException;

public class Login extends Application {
    private final String title = "Login";

    private static Stage window;

    public static Stage getWindow() {
        return window;
    }

    private void setWindow(Stage window) {
        Login.window = window;
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Login.class.getResource("/view/fxml/login.fxml"));
            Scene scene = new Scene(root);
            setWindow(stage);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            stage.getIcons().add(new Icon().getImage());
        } catch (IOException e) {
            System.out.println(String.format("ERRO (%s)", title));
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}