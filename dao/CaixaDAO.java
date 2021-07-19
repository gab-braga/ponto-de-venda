package dao;

import model.Caixa;
import model.Venda;

import java.sql.*;

public abstract class CaixaDAO {

    private static boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS caixa(" +
                    "CAIXA_CODIGO INT AUTO_INCREMENT PRIMARY KEY," +
                    "CAIXA_VALOR DOUBLE NOT NULL," +
                    "CAIXA_DATA_HORA DATETIME NOT NULL," +
                    "CAIXA_NATUREZA INT NOT NULL" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE BOX): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    public static boolean register(Caixa caixa) {
        createTable();
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO caixa (CAIXA_VALOR, CAIXA_DATA_HORA, CAIXA_NATUREZA) VALUES (?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setDouble(1, caixa.getValor());
            statement.setTimestamp(2, new Timestamp(caixa.getDataHora().getTime()));
            statement.setInt(3, caixa.getNatureza());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER BOX): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
