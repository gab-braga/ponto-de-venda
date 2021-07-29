package dao;

import model.Saida;
import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class UsuarioDAO {

    private static List<Usuario> getUserList(ResultSet results) throws Exception {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        while (results.next()) {
            String nome = results.getString("USUARIO_NOME");
            String senha = results.getString("USUARIO_SENHA");
            String permissao = results.getString("USUARIO_PERMISSAO");
            usuarios.add(new Usuario(nome, senha, permissao));
        }
        return usuarios;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if(ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql =
                                "CREATE TABLE IF NOT EXISTS usuario(" +
                                        "USUARIO_NOME VARCHAR(100)," +
                                        "USUARIO_SENHA VARCHAR(50) NOT NULL," +
                                        "USUARIO_PERMISSAO VARCHAR(80) NOT NULL," +
                                        "PRIMARY KEY (USUARIO_NOME)" +
                                        ")" +
                                        "ENGINE=InnoDB;";
                        Statement statement = ConnectionFactory.connection.createStatement();
                        statement.execute(sql);
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (CREATE TABLE USER): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean register(Usuario usuario) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
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
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean deleteByName(String nome) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "DELETE FROM usuario WHERE USUARIO_NOME = ?;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, nome);
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (DELETE USER BY NAME): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Usuario> queryUserPassword(String usuario, String senha) {
        List<Usuario> results = new ArrayList<Usuario>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM usuario WHERE USUARIO_NOME = ? AND USUARIO_SENHA = ?;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, usuario);
                        statement.setString(2, senha);
                        results = getUserList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY USER AND PASSWORD): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Usuario> queryAllUser() {
        List<Usuario> results = new ArrayList<Usuario>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM usuario ORDER BY USUARIO_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        results = getUserList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL USER): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Usuario> queryUserByName(String name) {
        List<Usuario> results = new ArrayList<Usuario>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM usuario WHERE USUARIO_NOME LIKE '%" + name + "%' ORDER BY USUARIO_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        results = getUserList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY USER BY NAME): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Usuario> queryUserByPermission(String permission) {
        List<Usuario> results = new ArrayList<Usuario>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM usuario WHERE USUARIO_PERMISSAO = ? ORDER BY USUARIO_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, permission);
                        results = getUserList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY USER BY PERMISSION): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Usuario> queryUserByNameOrPermission(String name, String permission) {
        List<Usuario> results = new ArrayList<Usuario>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM usuario WHERE USUARIO_NOME LIKE '%" + name + "%' OR USUARIO_PERMISSAO = ? ORDER BY USUARIO_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, permission);
                        results = getUserList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY USER BY NAME OR PERMISSION): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    protected static Usuario getUserByName(String nome) {
        return queryUserByName(nome).get(0);
    }
}
