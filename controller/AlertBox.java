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

    public static void onlyNumbers(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Digite apenas números nos campos numéricos!");
        alert.showAndWait();
    }

    public static void invalidQuantityValue(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Valor de quantidade inválido!");
        alert.showAndWait();
    }

    public static void unregisteredProduct(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Produto não cadastrado!");
        alert.showAndWait();
    }

    public static void productAlreadyStocked(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Este produto já existe no estoque!");
        alert.showAndWait();
    }

    public static void productAlreadyRegistered(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Este produto já está cadastrado!");
        alert.showAndWait();
    }

    public static void userAlreadyRegistered(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Este usuário já está cadastrado!");
        alert.showAndWait();
    }

    public static void stockUp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Estoque alimentado.");
        alert.showAndWait();
    }
}
