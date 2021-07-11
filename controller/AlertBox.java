package controller;

import javafx.scene.control.Alert;

public class AlertBox {
    public static void registrationCompleted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Cadastrado com sucesso.");
        alert.showAndWait();
    }

    public static void registrationError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar cadastro.");
        alert.showAndWait();
    }

    public static void fillAllFields(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Preencha todos os campos obrigatórios!");
        alert.showAndWait();
    }

    public static void incorrectUserOrPassword(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Usuário e/ou Senha incorreto!");
        alert.showAndWait();
    }
}
