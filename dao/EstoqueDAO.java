package dao;

import model.Estoque;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class EstoqueDAO {

    public EstoqueDAO() {
        createTable();
    }

    private boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS estoque(" +
                    "ESTOQUE_CODIGO INT AUTO_INCREMENT PRIMARY KEY," +
                    "ESTOQUE_TIPO_EMBALADO VARCHAR(100) NOT NULL," +
                    "ESTOQUE_QUANTIDADE INT NOT NULL," +
                    "PRODUTO_CODIGO INT NOT NULL" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE STOCK): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    // INSERIR
    public static boolean register(Estoque estoque) {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO estoque (ESTOQUE_TIPO_EMBALADO, ESTOQUE_QUANTIDADE, PRODUTO_CODIGO) VALUES (?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setString(1, estoque.getTipoEmbalado());
            statement.setInt(2, estoque.getQuantidade());
            statement.setInt(3, estoque.getProduto().getCodigo());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER STOCK): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
