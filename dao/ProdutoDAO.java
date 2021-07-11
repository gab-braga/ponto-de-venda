package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ProdutoDAO {

    public ProdutoDAO() {
        createTable();
    }

    private boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS produto(" +
                    "PRODUTO_CODIGO INT PRIMARY KEY," +
                    "PRODUTO_DESCRICAO VARCHAR(200) NOT NULL," +
                    "PRODUTO_VALOR_VENDA DOUBLE NOT NULL" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE PRODUCT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    // INSERIR
    public static boolean register(Produto produto) {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO cliente (PRODUTO_CODIGO, PRODUTO_DESCRICAO, PRODUTO_VALOR_VENDA) VALUES (?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setInt(1, produto.getCodigo());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getValorVenda());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER PRODUCT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
