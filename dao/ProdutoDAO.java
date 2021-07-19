package dao;

import model.Produto;
import model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class ProdutoDAO {

    private static boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS produto(" +
                    "PRODUTO_CODIGO INT PRIMARY KEY," +
                    "PRODUTO_DESCRICAO VARCHAR(200) NOT NULL," +
                    "PRODUTO_VALOR_VENDA DOUBLE NOT NULL" +
                    ")" +
                    "ENGINE=InnoDB;";
            Statement statement = ConnectionFactory.connection.createStatement();
            statement.execute(sql);
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (CREATE TABLE PRODUCT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    private static List<Produto> getProductsList(ResultSet results) throws Exception {
        List<Produto> produtos = new ArrayList<Produto>();
        while (results.next()) {
            int codigo = results.getInt("PRODUTO_CODIGO");
            String descricao = results.getString("PRODUTO_DESCRICAO");
            double valorVenda = results.getDouble("PRODUTO_VALOR_VENDA");
            produtos.add(new Produto(codigo, descricao, valorVenda));
        }
        return produtos;
    }

    public static boolean register(Produto produto) {
        createTable();
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO produto (PRODUTO_CODIGO, PRODUTO_DESCRICAO, PRODUTO_VALOR_VENDA) VALUES (?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setInt(1, produto.getCodigo());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getValorVenda());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER PRODUCT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    public static boolean update(Produto produto) {
        createTable();
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "UPDATE produto SET PRODUTO_CODIGO = ?, PRODUTO_DESCRICAO = ?, PRODUTO_VALOR_VENDA = ? WHERE PRODUTO_CODIGO = ?;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setInt(1, produto.getCodigo());
            statement.setString(2, produto.getDescricao());
            statement.setDouble(3, produto.getValorVenda());
            statement.setInt(4, produto.getCodigo());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (UPDATE PRODUCT): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    public static boolean deleteByCode(int codigo) {
        createTable();
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "DELETE p, e FROM produto AS p JOIN estoque AS e ON p.PRODUTO_CODIGO = e.PRODUTO_CODIGO  WHERE p.PRODUTO_CODIGO = ?;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setInt(1, codigo);
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (DELETE PRODUCT BY CODE): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }

    public static List<Produto> queryAllProducts() {
        createTable();
        List<Produto> results = new ArrayList<Produto>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM produto ORDER BY PRODUTO_DESCRICAO;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            results = getProductsList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY ALL PRODUCTS): " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionFactory.closeConnection();
        return results;
    }

    public static List<Produto> queryByCodeProducts(int code) {
        createTable();
        List<Produto> results = new ArrayList<Produto>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM produto WHERE PRODUTO_CODIGO = ? ORDER BY PRODUTO_CODIGO;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setInt(1, code);
            results = getProductsList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY PRODUCT BY CODE): " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionFactory.closeConnection();
        return results;
    }

    public static List<Produto> queryByDescriptionProducts(String description) {
        createTable();
        List<Produto> results = new ArrayList<Produto>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM produto WHERE PRODUTO_DESCRICAO LIKE '%"+ description +"%' ORDER BY PRODUTO_DESCRICAO;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            results = getProductsList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY PRODUCT BY DESCRIPTION): " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionFactory.closeConnection();
        return results;
    }

    public static List<Produto> queryByCodeOrDescriptionProducts(int code, String description) {
        createTable();
        List<Produto> results = new ArrayList<Produto>();
        ConnectionFactory.openConnection();
        try {
            String sql = "SELECT * FROM produto WHERE PRODUTO_CODIGO = "+ code +" OR PRODUTO_DESCRICAO LIKE '%"+ description +"%' ORDER BY PRODUTO_DESCRICAO;";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            results = getProductsList(statement.executeQuery());
        } catch (SQLException e) {
            System.err.println("ERRO (QUERY PRODUCT BY CODE OR DESCRIPTION): " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConnectionFactory.closeConnection();
        return results;
    }
}