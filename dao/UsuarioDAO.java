package dao;

import model.Cliente;
import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    public UsuarioDAO() {
        createTable();
    }

    private boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS usuario(" +
                    "USUARIO_NOME VARCHAR(100) PRIMARY KEY," +
                    "USUARIO_SENHA VARCHAR(50) NOT NULL," +
                    "USUARIO_PERMISSAO VARCHAR(80) NOT NULL" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE USER): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    // INSERIR
    public static boolean register(Usuario usuario) {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO usuario (USUARIO_NOME, USUARIO_SENHA, USUARIO_PERMISSAO) VALUES (?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setString(1, usuario.getNome());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getPermissao());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER USER): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
