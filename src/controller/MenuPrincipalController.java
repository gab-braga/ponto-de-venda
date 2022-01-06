package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuPrincipalController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MenuItem itemSeller;

    @FXML
    private MenuItem itemRemove;

    @FXML
    private MenuItem itemConsultProduct;

    @FXML
    private MenuItem itemConsultClient;

    @FXML
    private MenuItem itemConsultStock;

    @FXML
    private MenuItem itemConsultSales;

    @FXML
    private MenuItem itemConsultExits;

    @FXML
    private MenuItem itemRegisterProduct;

    @FXML
    private MenuItem itemRegisterClient;

    @FXML
    private MenuItem itemRegisterStock;

    @FXML
    private MenuItem itemDailyReport;

    @FXML
    private MenuItem itemMonthlyReport;

    @FXML
    private MenuItem itemAnnualReport;

    @FXML
    private MenuItem itemUsers;

    @FXML
    private MenuItem itemExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blockFullAccess();

        itemSeller.setOnAction(event -> {
            Caixa caixa = new Caixa();
            caixa.start(new Stage());
        });

        itemRemove.setOnAction(event -> {
            Retirar retirar = new Retirar();
            retirar.start(new Stage());
        });

        itemConsultProduct.setOnAction(event -> {
            ConsultarProdutos consultarProdutos = new ConsultarProdutos();
            consultarProdutos.start(new Stage());
        });

        itemConsultClient.setOnAction(event -> {
            ConsultarClientes consultarClientes = new ConsultarClientes();
            consultarClientes.start(new Stage());
        });

        itemConsultStock.setOnAction(event -> {
            ConsultarEstoque consultarEstoque = new ConsultarEstoque();
            consultarEstoque.start(new Stage());
        });

        itemConsultSales.setOnAction(event -> {
            ConsultarVendas consultarVendas = new ConsultarVendas();
            consultarVendas.start(new Stage());
        });

        itemConsultExits.setOnAction(event -> {
            ConsultarSaidas consultarSaidas = new ConsultarSaidas();
            consultarSaidas.start(new Stage());
        });

        itemRegisterProduct.setOnAction(event -> {
            CadastrarProduto cadastrarProduto = new CadastrarProduto();
            cadastrarProduto.start(new Stage());
        });

        itemRegisterClient.setOnAction(event -> {
            CadastrarCliente cadastrarCliente = new CadastrarCliente();
            cadastrarCliente.start(new Stage());
        });

        itemRegisterStock.setOnAction(event -> {
            CadastrarEstoque cadastrarEstoque = new CadastrarEstoque();
            cadastrarEstoque.start(new Stage());
        });

        itemDailyReport.setOnAction(event -> {
            RelatorioDiario relatorioDiario = new RelatorioDiario();
            relatorioDiario.start(new Stage());
        });

        itemMonthlyReport.setOnAction(event -> {
            RelatorioMensal relatorioMensal = new RelatorioMensal();
            relatorioMensal.start(new Stage());
        });

        itemAnnualReport.setOnAction(event -> {
            RelatorioAnual relatorioAnual = new RelatorioAnual();
            relatorioAnual.start(new Stage());
        });

        itemUsers.setOnAction(event -> {
            Usuarios usuarios = new Usuarios();
            usuarios.start(new Stage());
        });

        itemExit.setOnAction(event -> {
            close();
        });
    }

    private void blockFullAccess() {
        itemUsers.setVisible(Access.isFullAccess());
    }

    private void close() {
        ((Stage) this.rootPane.getScene().getWindow()).close();
    }
}
