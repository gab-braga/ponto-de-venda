package dao;

import model.Cliente;

import java.sql.*;

public class ClienteDAO {

    public ClienteDAO() {
        createTable();
    }

    private boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS cliente(" +
                    "CLIENTE_CODIGO INT AUTO_INCREMENT PRIMARY KEY," +
                    "CLIENTE_NOME VARCHAR(100) NOT NULL," +
                    "CLIENTE_CPF VARCHAR(80) NOT NULL," +
                    "CLIENTE_TELEFONE VARCHAR(15) NOT NULL," +
                    "CLIENTE_EMAIL VARCHAR(100) DEFAULT '-'," +
                    "CLIENTE_ENDERECO VARCHAR(100) DEFAULT '-'," +
                    "CLIENTE_NUMERO VARCHAR(10) DEFAULT '-'," +
                    "CLIENTE_CIDADE VARCHAR(100) DEFAULT '-'," +
                    "CLIENTE_UF VARCHAR(10) DEFAULT '-'" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE CLIENT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    // INSERIR
    public static boolean register(Cliente cliente) {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO cliente (CLIENTE_NOME, CLIENTE_CPF, CLIENTE_TELEFONE, CLIENTE_EMAIL, CLIENTE_ENDERECO, CLIENTE_NUMERO, CLIENTE_CIDADE, CLIENTE_UF) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCpf());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getEmail());
            statement.setString(5, cliente.getEndereco());
            statement.setString(6, cliente.getNumero());
            statement.setString(7, cliente.getCidade());
            statement.setString(8, cliente.getUf());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER CLIENT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
