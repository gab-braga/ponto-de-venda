package dao;

import model.Saida;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SaidaDAO {

    public SaidaDAO() {
        createTable();
    }

    private boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS saida(" +
                    "SAIDA_CODIGO INT AUTO_INCREMENT PRIMARY KEY," +
                    "SAIDA_VALOR DOUBLE NOT NULL," +
                    "SAIDA_DATA_HORA DATE NOT NULL," +
                    "SAIDA_MOTIVO TEXT NOT NULL," +
                    "SAIDA_OPERADOR VARCHAR(100) DEFAULT '-'" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE EXIT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    // INSERIR
    public static boolean register(Saida saida) {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO saida (SAIDA_VALOR, SAIDA_DATA_HORA, SAIDA_MOTIVO, SAIDA_OPERADOR) VALUES (?, ?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setDouble(1, saida.getValor());
            statement.setDate(2, (Date) saida.getDataHora());
            statement.setString(3, saida.getMotivo());
            statement.setString(4, saida.getOperador());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER EXIT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
