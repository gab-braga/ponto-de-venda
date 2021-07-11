package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    final static private String url = "jdbc:mysql://127.0.0.1:3306/pontodevenda";
    final static private String usuario = "usuario";
    final static private String senha = "senha";

    protected static Connection connection = null;

    protected static void openConnection() {
        try {
            connection = DriverManager.getConnection(url, usuario, senha);
        }
        catch(SQLException e) {
            System.err.println("ERRO (OPEN CONNECTION): " + e.getCause());
        }
    }

    protected static void closeConnection() {
        try {
            connection.close();
        }
        catch(SQLException e) {
            System.err.println("ERRO (CLOSE CONNECTION): " + e.getCause());
        }
    }

    protected static boolean createDatabase() {
        boolean flag = false;
        openConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS pontodevenda;";
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE DATABASE): " + e.getCause());
        }
        closeConnection();
        return flag;
    }
}
