package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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

    public static void operationCompleted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Operação efetuada com sucesso.");
        alert.showAndWait();
    }

    public static void operationError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar operação.");
        alert.showAndWait();
    }

    public static void editionCompleted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Editado com sucesso.");
        alert.showAndWait();
    }

    public static void editionError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar edição.");
        alert.showAndWait();
    }

    public static void deleteCompleted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Excluido com sucesso.");
        alert.showAndWait();
    }

    public static void deleteError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao efetuar exclusão.");
        alert.showAndWait();
    }

    public static void sallerCompleted(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AVISO");
        alert.setHeaderText(null);
        alert.setContentText("Venda realizada com sucesso.");
        alert.showAndWait();
    }

    public static void insufficientStock() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Estoque insuficiente!");
        alert.showAndWait();
    }

    public static void insufficientValueReceived() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Valor recebido insuficiente!");
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
        alert.setContentText("Valor quantitativo inválido!");
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

    public static void stockUpError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERRO");
        alert.setHeaderText(null);
        alert.setContentText("Erro ao alimentar o estoque.");
        alert.showAndWait();
    }

    public static void selectARecord(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Selecione um registro!");
        alert.showAndWait();
    }

    public static boolean confirmationDelete() {
        boolean flag = false;
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(null);
        confirmation.setContentText("Cofirma exclusão?");
        confirmation.setTitle("CONFIRMAÇÃO");
        Optional<ButtonType> response = confirmation.showAndWait();
        if (response.get() == ButtonType.OK) {
            flag = true;
        }
        return flag;
    }

    public static void dateInvalided(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Formato de data inválido!");
        alert.showAndWait();
    }

    public static void insertAProduct() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Insira um produto!");
        alert.showAndWait();
    }

    public static void insertAClient() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("Insira um cliente!");
        alert.showAndWait();
    }

    public static void amountChargedIsInsufficient() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATENÇÃO");
        alert.setHeaderText(null);
        alert.setContentText("O valor recebido é insuficiente!");
        alert.showAndWait();
    }
}
