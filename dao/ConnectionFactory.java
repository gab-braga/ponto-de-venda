package dao;

import java.sql.*;

public abstract class ConnectionFactory {

    final static protected String database = "pontodevenda";
    final static private String url = "jdbc:mysql://127.0.0.1:3306/";
    final static private String usuario = "usuario";
    final static private String senha = "senha";

    protected static Connection connection = null;

    protected static boolean openConnection() {
        boolean flag = false;
        try {
            connection = DriverManager.getConnection(url, usuario, senha);
            flag = true;
        }
        catch(SQLException e) {
            System.err.println("ERRO (OPEN CONNECTION): " + e.getCause());
        }
        return flag;
    }

    protected static boolean closeConnection() {
        boolean flag = false;
        try {
            connection.close();
            flag = true;
        }
        catch(SQLException e) {
            System.err.println("ERRO (CLOSE CONNECTION): " + e.getCause());
        }
        return flag;
    }

    protected static boolean createDatabase() {
        boolean flag = false;
        if(openConnection()) {
            try {
                Statement statement = connection.createStatement();
                String sql = "CREATE DATABASE IF NOT EXISTS pontodevenda;";
                statement.execute(sql);
                flag = true;
            } catch (SQLException e) {
                System.err.println("ERRO (CREATE DATABASE): " + e.getCause());
            }
            closeConnection();
        }
        return flag;
    }

    protected static boolean useDataBase() {
        boolean flag = false;
        try {
            if(!connection.isClosed()) {
                Statement statement = connection.createStatement();
                String sql = "USE "+ database +";";
                statement.execute(sql);
                flag = true;
            }
        } catch (SQLException e) {
            System.err.println("ERRO (USE DATABASE): " + e.getCause());
        }
        return flag;
    }

    protected static int getKeyTable(ResultSet results) throws SQLException {
        return results.getInt(1);
    }
}
