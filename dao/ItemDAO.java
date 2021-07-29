package dao;

import model.Caixa;
import model.Item;
import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ItemDAO {

    private static List<Item> getItemsList(ResultSet results) throws SQLException {
        List<Item> items = new ArrayList<Item>();
        while (results.next()) {
            int code = results.getInt("ITEM_CODIGO");
            int quantity = results.getInt("ITEM_QUANTIDADE");
            int codeProduct = results.getInt("PRODUTO_CODIGO");
            int codeSale = results.getInt("VENDA_CODIGO");
            items.add(new Item(code, ProdutoDAO.getProductByCode(codeProduct), VendaDAO.getSaleByCode(codeSale), quantity));
        }
        return items;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if (ProdutoDAO.createTable() && VendaDAO.createTable()) {
                if (ConnectionFactory.openConnection()) {
                    if(ConnectionFactory.useDataBase()) {
                        try {
                            String sql =
                                    "CREATE TABLE IF NOT EXISTS item(" +
                                            "ITEM_CODIGO INT AUTO_INCREMENT," +
                                            "ITEM_QUANTIDADE INT NOT NULL," +
                                            "PRODUTO_CODIGO INT NOT NULL," +
                                            "VENDA_CODIGO INT NOT NULL," +
                                            "PRIMARY KEY (ITEM_CODIGO)," +
                                            "FOREIGN KEY (PRODUTO_CODIGO) REFERENCES produto (PRODUTO_CODIGO)," +
                                            "FOREIGN KEY (VENDA_CODIGO) REFERENCES venda (VENDA_CODIGO)" +
                                            ")" +
                                            "ENGINE=InnoDB;";
                            Statement statement = ConnectionFactory.connection.createStatement();
                            statement.execute(sql);
                            flag = true;
                        } catch (SQLException e) {
                            System.err.println("ERRO (CREATE TABLE ITEM): " + e.getMessage());
                        }
                    }
                    ConnectionFactory.closeConnection();
                }
            }
        }
        return flag;
    }

    public static boolean register(Item item) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "INSERT INTO item (ITEM_QUANTIDADE, PRODUTO_CODIGO, VENDA_CODIGO) VALUES (?, ?, ?);";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, item.getQuantidade());
                        statement.setInt(2, item.getProduto().getCodigo());
                        statement.setInt(3, item.getVenda().getCodigo());
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (REGISTER ITEM): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Item> queryAllItems() {
        List<Item> items = new ArrayList<Item>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM item ORDER BY ITEM_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        items = getItemsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL ITEMS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return items;
    }

    public static List<Item> queryItemsByCodeProductOrCodeSale(Item item) {
        List<Item> items = new ArrayList<Item>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM item WHERE PRODUTO_CODIGO = ? OR VENDA_CODIGO = ? ORDER BY ITEM_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, item.getProduto().getCodigo());
                        statement.setInt(2, item.getVenda().getCodigo());
                        items = getItemsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ITEMS BY PRODUCT CODE OR SALE CODE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return items;
    }

    public static List<Item> queryItemByCode(int codigo) {
        List<Item> items = new ArrayList<Item>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM item WHERE ITEM_CODIGO = ? ORDER BY ITEM_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, codigo);
                        items = getItemsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ITEM BY CODE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return items;
    }

    protected static Item getItemByCode(int codigo) {
        return queryItemByCode(codigo).get(0);
    }
}
