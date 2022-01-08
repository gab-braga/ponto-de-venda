package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class VendaDAO {

    private static List<Venda> getSalesList(ResultSet results) throws SQLException {
        List<Venda> vendas = new ArrayList<Venda>();
        while (results.next()) {
            int code = results.getInt("VENDA_CODIGO");
            Date date = results.getDate("VENDA_DATA");
            Time hour = results.getTime("VENDA_HORA");
            Double value = results.getDouble("VENDA_VALOR");
            int codeClient = results.getInt("CLIENTE_CODIGO");
            int codeCaixa = results.getInt("CAIXA_CODIGO");
            String nameUsuario = results.getString("USUARIO_NOME");
            vendas.add(new Venda(code, value, Datetime.getTimestamp(date, hour), ClienteDAO.getClientByCode(codeClient), CaixaDAO.getBoxByCode(codeCaixa), UsuarioDAO.getUserByName(nameUsuario)));
        }
        return vendas;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if (ClienteDAO.createTable() && CaixaDAO.createTable() && UsuarioDAO.createTable()) {
                if (ConnectionFactory.openConnection()) {
                    if (ConnectionFactory.useDataBase()) {
                        try {
                            String sql =
                                    "CREATE TABLE IF NOT EXISTS venda(" +
                                            "VENDA_CODIGO INT AUTO_INCREMENT," +
                                            "VENDA_VALOR DOUBLE NOT NULL," +
                                            "VENDA_DATA DATE NOT NULL," +
                                            "VENDA_HORA TIME NOT NULL," +
                                            "CLIENTE_CODIGO INT NOT NULL," +
                                            "CAIXA_CODIGO INT NOT NULL," +
                                            "USUARIO_NOME VARCHAR(100) NOT NULL," +
                                            "PRIMARY KEY (VENDA_CODIGO)," +
                                            "FOREIGN KEY (CLIENTE_CODIGO) REFERENCES cliente (CLIENTE_CODIGO) ON DELETE CASCADE ON UPDATE CASCADE," +
                                            "FOREIGN KEY (CAIXA_CODIGO) REFERENCES caixa (CAIXA_CODIGO)," +
                                            "FOREIGN KEY (USUARIO_NOME) REFERENCES usuario (USUARIO_NOME)" +
                                            ")" +
                                            "ENGINE=InnoDB;";
                            Statement statement = ConnectionFactory.connection.createStatement();
                            statement.execute(sql);
                            flag = true;
                        } catch (SQLException e) {
                            System.err.println("ERRO (CREATE TABLE SALE): " + e.getMessage());
                        }
                    }
                    ConnectionFactory.closeConnection();
                }
            }
        }
        return flag;
    }

    public static boolean register(Venda venda) {
        boolean flag = false;
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "INSERT INTO venda (VENDA_VALOR, VENDA_DATA, VENDA_HORA, CLIENTE_CODIGO, CAIXA_CODIGO, USUARIO_NOME) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        statement.setDouble(1, venda.getValor());
                        statement.setDate(2, new Date(venda.getDataHora().getTime()));
                        statement.setTime(3, new Time(venda.getDataHora().getTime()));
                        statement.setInt(4, venda.getCliente().getCodigo());
                        statement.setInt(5, venda.getCaixa().getCodigo());
                        statement.setString(6, venda.getOperator().getNome());
                        statement.executeUpdate();
                        ResultSet results = statement.getGeneratedKeys();
                        results.first();
                        int key = ConnectionFactory.getKeyTable(results);
                        venda.setCodigo(key);
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (REGISTER SALE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Venda> queryAllRegisters() {
        List<Venda> vendas = new ArrayList<Venda>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM venda ORDER BY VENDA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        vendas = getSalesList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL SALES): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return vendas;
    }

    public static List<Venda> querySalesByDate(java.util.Date date) {
        List<Venda> vendas = new ArrayList<Venda>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM venda WHERE VENDA_DATA = ? ORDER BY VENDA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setDate(1, new Date(date.getTime()));
                        vendas = getSalesList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY SALES BY DATE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return vendas;
    }

    public static List<Venda> querySaleByCode(int codigo) {
        List<Venda> vendas = new ArrayList<Venda>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM venda WHERE VENDA_CODIGO = ? ORDER BY VENDA_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, codigo);
                        vendas = getSalesList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY SALE BY CODE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return vendas;
    }

    protected static Venda getSaleByCode(int codigo) {
        List<Venda> vendas = querySaleByCode(codigo);
        Venda venda = null;
        if (vendas.size() > 0) {
            venda = vendas.get(0);
        }
        return venda;
    }
}
