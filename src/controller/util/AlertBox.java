package controller.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class AlertBox {

    private static final String INFORMATIVE_TITLE = "ATENÇÃO";
    private static final String ATTENTION_TITLE = "AVISO";
    private static final String ERROR_TITLE = "ERRO";
    private static final String CONFIRMATION_TITLE = "CONFIRMAÇÃO";

    public static void registrationCompleted() {
        configureAlertBox(AlertType.INFORMATION, INFORMATIVE_TITLE, "Cadastrado com sucesso.").showAndWait();
    }

    public static void registrationError() {
        configureAlertBox(AlertType.ERROR, ERROR_TITLE, "Erro ao efetuar cadastro.").showAndWait();
    }

    public static void operationCompleted() {
        configureAlertBox(AlertType.INFORMATION, INFORMATIVE_TITLE, "Operação efetuada com sucesso.").showAndWait();
    }

    public static void operationError() {
        configureAlertBox(AlertType.ERROR, ERROR_TITLE, "Erro ao efetuar operação.").showAndWait();
    }

    public static void editionCompleted() {
        configureAlertBox(AlertType.INFORMATION, INFORMATIVE_TITLE, "Editado com sucesso.").showAndWait();
    }

    public static void editionError() {
        configureAlertBox(AlertType.ERROR, ERROR_TITLE, "Erro ao efetuar edição.").showAndWait();
    }

    public static void deleteCompleted() {
        configureAlertBox(AlertType.INFORMATION, INFORMATIVE_TITLE, "Excluido com sucesso.").showAndWait();
    }

    public static void deleteError() {
        configureAlertBox(AlertType.ERROR, ERROR_TITLE, "Erro ao efetuar exclusão.").showAndWait();
    }

    public static void stockUp() {
        configureAlertBox(AlertType.INFORMATION, INFORMATIVE_TITLE, "Estoque alimentado.").showAndWait();
    }

    public static void stockUpError() {
        configureAlertBox(AlertType.ERROR, ERROR_TITLE, "Erro ao alimentar o estoque.").showAndWait();
    }

    public static void sallerCompleted() {
        configureAlertBox(AlertType.INFORMATION, INFORMATIVE_TITLE, "Venda realizada com sucesso.").showAndWait();
    }

    public static void insufficientStock() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Estoque insuficiente!").showAndWait();
    }

    public static void insufficientValueReceived() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Valor recebido insuficiente!").showAndWait();
    }

    public static void fillAllFields() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Preencha todos os campos obrigatórios!").showAndWait();
    }

    public static void incorrectUserOrPassword() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Usuário e/ou Senha incorreto!").showAndWait();
    }

    public static void onlyNumbers() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Digite apenas números nos campos numéricos!").showAndWait();
    }

    public static void invalidQuantityValue() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Valor quantitativo inválido!").showAndWait();
    }

    public static void unregisteredProduct() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Produto não cadastrado!").showAndWait();
    }

    public static void productAlreadyStocked() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Este produto já existe no estoque!").showAndWait();
    }

    public static void productStillExistsInStock() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "O produto ainda existe no estoque!").showAndWait();
    }

    public static void productAlreadyRegistered() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Este produto já está cadastrado!").showAndWait();
    }

    public static void userAlreadyRegistered() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Este usuário já está cadastrado!").showAndWait();
    }

    public static void selectARecord() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Selecione um registro!").showAndWait();
    }

    public static void dateInvalided() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Formato de data inválido!").showAndWait();
    }

    public static void insertAProduct() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Insira um produto!").showAndWait();
    }

    public static void insertAClient() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "Insira um cliente!").showAndWait();
    }

    public static void amountChargedIsInsufficient() {
        configureAlertBox(AlertType.WARNING, ATTENTION_TITLE, "O valor recebido é insuficiente!").showAndWait();
    }

    public static boolean confirmationDelete() {
        boolean isConfirme = false;
        Alert deletionAlertBox = configureAlertBox(AlertType.WARNING, CONFIRMATION_TITLE, "Deseja excluir?");
        Optional<ButtonType> response = deletionAlertBox.showAndWait();
        if (response.get() == ButtonType.OK) {
            isConfirme = true;
        }
        return isConfirme;
    }

    private static Alert configureAlertBox(AlertType type, String title, String message) {
        Alert alertBox = new Alert(type);
        alertBox.setTitle(title);
        alertBox.setHeaderText(null);
        alertBox.setContentText(message);
        return alertBox;
    }
}
