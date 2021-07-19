package dao;

import model.Saida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SaidaDAO {

    private static boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS saida(" +
                    "SAIDA_CODIGO INT AUTO_INCREMENT PRIMARY KEY," +
                    "SAIDA_VALOR DOUBLE NOT NULL," +
                    "SAIDA_DATA DATE NOT NULL," +
                    "SAIDA_HORA TIME NOT NULL," +
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

    private static List<Saida> getExitsList(ResultSet results) throws SQLException {
        List<Saida> saidas = new ArrayList<Saida>();
        while (results.next()) {
            int code = results.getInt("SAIDA_CODIGO");
            Date date = results.getDate("SAIDA_DATA");
            Time hour = results.getTime("SAIDA_HORA");
            Double value = results.getDouble("SAIDA_VALOR");
            String reason = results.getString("SAIDA_MOTIVO");
            String operator = results.getString("SAIDA_OPERADOR");
            saidas.add(new Saida(code, value, Datetime.getTimestamp(date, hour), reason, operator));
        }
        return saidas;
    }

    public static boolean register(Saida saida) {
        createTable();
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO saida (SAIDA_VALOR, SAIDA_DATA, SAIDA_HORA, SAIDA_MOTIVO, SAIDA_OPERADOR) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setDouble(1, saida.getValor());
            statement.setDate(2, new Date(saida.getDataHora().getTime()));
            statement.setTime(3, new Time(saida.getDataHora().getTime()));
            statement.setString(4, saida.getMotivo());
            statement.setString(5, saida.getOperador());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER EXIT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    public static List<Saida> queryAllExits() {
        createTable();
        List<Saida> saidas = new ArrayList<Saida>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM saida ORDER BY SAIDA_DATA;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            saidas = getExitsList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY ALL EXITS): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return saidas;
    }

    public static List<Saida> queryExitsByDate(java.util.Date date) {
        createTable();
        List<Saida> saidas = new ArrayList<Saida>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM saida WHERE SAIDA_DATA = ? ORDER BY SAIDA_DATA;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setDate(1, new Date(date.getTime()));
            saidas = getExitsList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY EXITS BY DATE): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return saidas;
    }
}
