package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {

    private static Stage window;

    public static Stage getWindow() {
        return window;
    }

    private void setWindow(Stage window) {
        Login.window = window;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Login.class.getResource("fxml/login.fxml"));
        Scene scene = new Scene(root);
        setWindow(stage);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}