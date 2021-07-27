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
    private AnchorPane root;

    @FXML
    private MenuItem item_seller;

    @FXML
    private MenuItem item_remove;

    @FXML
    private MenuItem item_consult_product;

    @FXML
    private MenuItem item_consult_client;

    @FXML
    private MenuItem item_consult_stock;

    @FXML
    private MenuItem item_consult_sales;

    @FXML
    private MenuItem item_consult_exits;

    @FXML
    private MenuItem item_register_product;

    @FXML
    private MenuItem item_register_client;

    @FXML
    private MenuItem item_register_stock;

    @FXML
    private MenuItem item_daily_report;

    @FXML
    private MenuItem item_monthly_report;

    @FXML
    private MenuItem item_annual_report;

    @FXML
    private MenuItem item_users;

    @FXML
    private MenuItem item_close;

    private void close() {
        ((Stage) this.root.getScene().getWindow()).close();
    }

    private void blockFullAccess() {
        item_users.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        blockFullAccess();

        item_seller.setOnAction(event -> {
            Caixa caixa = new Caixa();
            caixa.start(new Stage());
        });

        item_remove.setOnAction(event -> {
            Retirar retirar = new Retirar();
            retirar.start(new Stage());
        });

        item_consult_product.setOnAction(event -> {
            ConsultarProdutos consultarProdutos = new ConsultarProdutos();
            consultarProdutos.start(new Stage());
        });

        item_consult_client.setOnAction(event -> {
            ConsultarClientes consultarClientes = new ConsultarClientes();
            consultarClientes.start(new Stage());
        });

        item_consult_stock.setOnAction(event -> {
            ConsultarEstoque consultarEstoque = new ConsultarEstoque();
            consultarEstoque.start(new Stage());
        });

        item_consult_sales.setOnAction(event -> {
            ConsultarVendas consultarVendas = new ConsultarVendas();
            consultarVendas.start(new Stage());
        });

        item_consult_exits.setOnAction(event -> {
            ConsultarSaidas consultarSaidas = new ConsultarSaidas();
            consultarSaidas.start(new Stage());
        });

        item_register_product.setOnAction(event -> {
            CadastrarProduto cadastrarProduto = new CadastrarProduto();
            cadastrarProduto.start(new Stage());
        });

        item_register_client.setOnAction(event -> {
            CadastrarCliente cadastrarCliente = new CadastrarCliente();
            cadastrarCliente.start(new Stage());
        });

        item_register_stock.setOnAction(event -> {
            CadastrarEstoque cadastrarEstoque = new CadastrarEstoque();
            cadastrarEstoque.start(new Stage());
        });

        item_daily_report.setOnAction(event -> {
            RelatorioDiario relatorioDiario = new RelatorioDiario();
            relatorioDiario.start(new Stage());
        });

        item_monthly_report.setOnAction(event -> {
            RelatorioMensal relatorioMensal = new RelatorioMensal();
            relatorioMensal.start(new Stage());
        });

        item_annual_report.setOnAction(event -> {
            RelatorioAnual relatorioAnual = new RelatorioAnual();
            relatorioAnual.start(new Stage());
        });

        item_users.setOnAction(event -> {
            Usuarios usuarios = new Usuarios();
            usuarios.start(new Stage());
        });

        item_close.setOnAction(event -> {
            close();
        });
    }
}
