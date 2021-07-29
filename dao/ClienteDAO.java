package dao;

import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ClienteDAO {

    private static List<Cliente> getClientsList(ResultSet results) throws SQLException {
        List<Cliente> clientes = new ArrayList<Cliente>();
        while (results.next()) {
            int codigo = results.getInt("CLIENTE_CODIGO");
            String nome = results.getString("CLIENTE_NOME");
            String cpf = results.getString("CLIENTE_CPF");
            String telefone = results.getString("CLIENTE_TELEFONE");
            String email = results.getString("CLIENTE_EMAIL");
            String endereco = results.getString("CLIENTE_ENDERECO");
            String numero = results.getString("CLIENTE_NUMERO");
            String cidade = results.getString("CLIENTE_CIDADE");
            String uf = results.getString("CLIENTE_UF");
            clientes.add(new Cliente(codigo, nome, cpf, telefone, email, endereco, numero, cidade, uf));
        }
        return clientes;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if(ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql =
                                "CREATE TABLE IF NOT EXISTS cliente(" +
                                        "CLIENTE_CODIGO INT AUTO_INCREMENT," +
                                        "CLIENTE_NOME VARCHAR(100) NOT NULL," +
                                        "CLIENTE_CPF VARCHAR(80) NOT NULL," +
                                        "CLIENTE_TELEFONE VARCHAR(15) NOT NULL," +
                                        "CLIENTE_EMAIL VARCHAR(100) DEFAULT '-'," +
                                        "CLIENTE_ENDERECO VARCHAR(100) DEFAULT '-'," +
                                        "CLIENTE_NUMERO VARCHAR(10) DEFAULT '-'," +
                                        "CLIENTE_CIDADE VARCHAR(100) DEFAULT '-'," +
                                        "CLIENTE_UF VARCHAR(10) DEFAULT '-'," +
                                        "PRIMARY KEY (CLIENTE_CODIGO)" +
                                        ")" +
                                        "ENGINE=InnoDB;";
                        Statement statement = ConnectionFactory.connection.createStatement();
                        statement.execute(sql);
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (CREATE TABLE CLIENT): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean register(Cliente cliente) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
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
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean update(Cliente cliente) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "UPDATE cliente SET CLIENTE_NOME = ?, CLIENTE_CPF = ?, CLIENTE_TELEFONE = ?, CLIENTE_EMAIL = ?, CLIENTE_ENDERECO = ?, CLIENTE_NUMERO = ?, CLIENTE_CIDADE = ?, CLIENTE_UF = ? WHERE CLIENTE_CODIGO = ?;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, cliente.getNome());
                        statement.setString(2, cliente.getCpf());
                        statement.setString(3, cliente.getTelefone());
                        statement.setString(4, cliente.getEmail());
                        statement.setString(5, cliente.getEndereco());
                        statement.setString(6, cliente.getNumero());
                        statement.setString(7, cliente.getCidade());
                        statement.setString(8, cliente.getUf());
                        statement.setInt(9, cliente.getCodigo());
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (UPDATE CLIENT): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean deleteByCode(int codigo) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "DELETE FROM cliente WHERE CLIENTE_CODIGO = ?;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, codigo);
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (DELETE CLIENT BY CODE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Cliente> queryAllClients() {
        List<Cliente> clientes = new ArrayList<Cliente>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM cliente ORDER BY CLIENTE_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        clientes = getClientsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL CLIENTS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return clientes;
    }

    public static List<Cliente> queryByNameClients(String name) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM cliente WHERE CLIENTE_NOME LIKE '%" + name + "%' ORDER BY CLIENTE_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        clientes = getClientsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BY NAME CLIENTS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return clientes;
    }

    public static List<Cliente> queryByCpfClients(String cpf) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM cliente WHERE CLIENTE_CPF = '" + cpf + "' ORDER BY CLIENTE_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        clientes = getClientsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BY CPF CLIENTS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return clientes;
    }

    public static List<Cliente> queryByNameOrCpfClients(String name, String cpf) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM cliente WHERE CLIENTE_NOME LIKE '%" + name + "%' OR CLIENTE_CPF = '" + cpf + "' ORDER BY CLIENTE_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        clientes = getClientsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BY NAME AND CPF CLIENTS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return clientes;
    }

    public static List<Cliente> queryClientByCode(int codigo) {
        List<Cliente> clientes = new ArrayList<Cliente>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM cliente WHERE CLIENTE_CODIGO = ? ORDER BY CLIENTE_NOME;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, codigo);
                        clientes = getClientsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BY CODE CLIENTS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return clientes;
    }

    protected static Cliente getClientByCode(int codigo) {
        return queryClientByCode(codigo).get(0);
    }
}
