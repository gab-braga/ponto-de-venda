package dao;

import model.Estoque;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class EstoqueDAO {

    private static List<Estoque> getStockList(ResultSet results) throws Exception {
        List<Estoque> estoque = new ArrayList<Estoque>();
        while (results.next()) {
            int codigo = results.getInt("ESTOQUE_CODIGO");
            String tipoEmbalado = results.getString("ESTOQUE_TIPO_EMBALADO");
            int quantidade = results.getInt("ESTOQUE_QUANTIDADE");
            int produto_codigo = results.getInt("PRODUTO_CODIGO");
            estoque.add(new Estoque(ProdutoDAO.getProductByCode(produto_codigo), codigo, tipoEmbalado, quantidade));
        }
        return estoque;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if(ProdutoDAO.createTable()) {
                if (ConnectionFactory.openConnection()) {
                    if(ConnectionFactory.useDataBase()) {
                        try {
                            String sql =
                                    "CREATE TABLE IF NOT EXISTS estoque(" +
                                            "ESTOQUE_CODIGO INT AUTO_INCREMENT," +
                                            "ESTOQUE_TIPO_EMBALADO VARCHAR(100) NOT NULL," +
                                            "ESTOQUE_QUANTIDADE INT NOT NULL," +
                                            "PRODUTO_CODIGO INT NOT NULL," +
                                            "PRIMARY KEY (ESTOQUE_CODIGO)," +
                                            "FOREIGN KEY (PRODUTO_CODIGO) REFERENCES produto (PRODUTO_CODIGO)" +
                                            ")" +
                                            "ENGINE=InnoDB;";
                            Statement statement = ConnectionFactory.connection.createStatement();
                            statement.execute(sql);
                            flag = true;
                        } catch (SQLException e) {
                            System.err.println("ERRO (CREATE TABLE STOCK): " + e.getMessage());
                        }
                    }
                    ConnectionFactory.closeConnection();
                }
            }
        }
        return flag;
    }

    public static boolean register(Estoque estoque) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "INSERT INTO estoque (ESTOQUE_TIPO_EMBALADO, ESTOQUE_QUANTIDADE, PRODUTO_CODIGO) VALUES (?, ?, ?);";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, estoque.getTipoEmbalado());
                        statement.setInt(2, estoque.getQuantidade());
                        statement.setInt(3, estoque.getProduto().getCodigo());
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (REGISTER STOCK): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean add(Estoque estoque) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "UPDATE estoque SET ESTOQUE_QUANTIDADE = ESTOQUE_QUANTIDADE + ? WHERE PRODUTO_CODIGO = ?;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, estoque.getQuantidade());
                        statement.setInt(2, estoque.getProduto().getCodigo());
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (ADD STOCK): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean decrease(Estoque estoque) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "UPDATE estoque SET ESTOQUE_QUANTIDADE = ESTOQUE_QUANTIDADE - ? WHERE PRODUTO_CODIGO = ?;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, estoque.getQuantidade());
                        statement.setInt(2, estoque.getProduto().getCodigo());
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (DECREASE STOCK): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Estoque> queryAllStock() {
        List<Estoque> results = new ArrayList<Estoque>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM estoque ORDER BY PRODUTO_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        results = getStockList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY STOCK BY CODE): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Estoque> queryStockByCode(int code) {
        List<Estoque> results = new ArrayList<Estoque>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM estoque WHERE PRODUTO_CODIGO = ? ORDER BY PRODUTO_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, code);
                        results = getStockList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY STOCK BY CODE): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Estoque> queryByDescription(String description) {
        List<Estoque> results = new ArrayList<Estoque>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT e.* FROM estoque AS e JOIN produto AS p ON e.PRODUTO_CODIGO = p.PRODUTO_CODIGO WHERE p.PRODUTO_DESCRICAO LIKE '%" + description + "%' ORDER BY p.PRODUTO_DESCRICAO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        results = getStockList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY STOCK BY DESCRIPTION): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static List<Estoque> queryByCodeOrDescription(int code, String description) {
        List<Estoque> results = new ArrayList<Estoque>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT e.* FROM estoque AS e JOIN produto AS p ON e.PRODUTO_CODIGO = p.PRODUTO_CODIGO WHERE e.PRODUTO_CODIGO = " + code + " OR p.PRODUTO_DESCRICAO LIKE '%" + description + "%' ORDER BY p.PRODUTO_DESCRICAO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        results = getStockList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY STOCK BY DESCRIPTION): " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return results;
    }

    public static Estoque getStockByCode(int codigo) {
        Estoque estoque = null;
        List<Estoque> estoqueList = queryStockByCode(codigo);
        if(estoqueList.size() > 0) {
            estoque = estoqueList.get(0);
        }
        return estoque;
    }
}
