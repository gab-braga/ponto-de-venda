package dao;

import model.Cliente;
import model.Item;
import model.Saida;
import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class VendaDAO {

    private static boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS venda(" +
                    "VENDA_CODIGO INT AUTO_INCREMENT PRIMARY KEY," +
                    "VENDA_VALOR DOUBLE NOT NULL," +
                    "VENDA_DATA DATE NOT NULL," +
                    "VENDA_HORA TIME NOT NULL," +
                    "VENDA_VENDEDOR VARCHAR(100) NOT NULL," +
                    "CLIENTE_CODIGO INT NOT NULL," +
                    "PRODUTO_CODIGO INT NOT NULL," +
                    "FOREIGN (CLIENTE_CODIGO) REFERENCES cliente," +
                    "FOREIGN (PRODUTO_CODIGO) REFERENCES produto" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE SALE): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    private static List<Venda> getSalesList(ResultSet results) throws SQLException {
        List<Venda> vendas = new ArrayList<Venda>();
        while (results.next()) {
            int code = results.getInt("VENDA_CODIGO");
            Date date = results.getDate("VENDA_DATA");
            Time hour = results.getTime("VENDA_HORA");
            Double value = results.getDouble("VENDA_VALOR");
            Cliente client = ClienteDAO.queryByCodeClient(results.getInt("CLIENTE_CODIGO")).get(0);
            String operator = results.getString("VENDA_VENDEDOR");
            vendas.add(new Venda(code, value, Datetime.getTimestamp(date, hour), operator, client));
        }
        return vendas;
    }

    private static int getKeyTable(ResultSet results) throws SQLException {
        return results.getInt("VENDA_CODIGO");
    }

    public static boolean register(Venda venda, Integer key) {
        createTable();
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO venda (VENDA_VALOR, VENDA_DATA, VENDA_HORA, VENDA_VENDEDOR, CLIENTE_CODIGO) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDouble(1, venda.getValor());
            statement.setDate(2, new Date(venda.getDataHora().getTime()));
            statement.setTime(3, new Time(venda.getDataHora().getTime()));
            statement.setString(4, venda.getVedendor());
            statement.setInt(5, venda.getCliente().getCodigo());
            statement.executeQuery();
            ResultSet results = statement.getGeneratedKeys();
            results.first();
            key = getKeyTable(results);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER SALE): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    public static List<Venda> queryAllSales() {
        createTable();
        List<Venda> vendas = new ArrayList<Venda>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM venda ORDER BY VENDA_DATA;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            vendas = getSalesList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY ALL SALES): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return vendas;
    }

    public static List<Venda> querySalesByDate(java.util.Date date) {
        createTable();
        List<Venda> vendas = new ArrayList<Venda>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM venda WHERE VENDA_DATA = ? ORDER BY VENDA_DATA;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setDate(1, new Date(date.getTime()));
            vendas = getSalesList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY SALES BY DATE): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return vendas;
    }
}
