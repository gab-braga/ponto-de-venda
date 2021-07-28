package dao;

import model.Caixa;
import model.Cliente;
import model.Usuario;
import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class VendaDAO {

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if(ClienteDAO.createTable() && CaixaDAO.createTable() && UsuarioDAO.createTable()) {
                if (ConnectionFactory.openConnection()) {
                    try {
                        String sql =
                                "CREATE TABLE IF NOT EXISTS " + ConnectionFactory.database + ".venda(" +
                                        "VENDA_CODIGO INT AUTO_INCREMENT," +
                                        "VENDA_VALOR DOUBLE NOT NULL," +
                                        "VENDA_DATA DATE NOT NULL," +
                                        "VENDA_HORA TIME NOT NULL," +
                                        "CLIENTE_CODIGO INT NOT NULL," +
                                        "CAIXA_CODIGO INT NOT NULL," +
                                        "USUARIO_NOME VARCHAR(100) NOT NULL," +
                                        "PRIMARY KEY (VENDA_CODIGO)," +
                                        "FOREIGN KEY (CLIENTE_CODIGO) REFERENCES cliente (CLIENTE_CODIGO)," +
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
                    ConnectionFactory.closeConnection();
                }
            }
        }
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
            Caixa caixa = CaixaDAO.queryBoxByCode(results.getInt("CAIXA_CODIGO")).get(0);
            Usuario usuario = UsuarioDAO.queryUserByName(results.getString("USUARIO_NOME")).get(0);
            vendas.add(new Venda(code, value, Datetime.getTimestamp(date, hour), client, caixa, usuario));
        }
        return vendas;
    }

    public static boolean register(Venda venda) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                try {
                    String sql = "INSERT INTO " + ConnectionFactory.database + ".venda (VENDA_VALOR, VENDA_DATA, VENDA_HORA, CLIENTE_CODIGO, CAIXA_CODIGO, USUARIO_NOME) VALUES (?, ?, ?, ?, ?, ?);";
                    PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, venda.getValor());
                    statement.setDate(2, new Date(venda.getDataHora().getTime()));
                    statement.setTime(3, new Time(venda.getDataHora().getTime()));
                    statement.setInt(4, venda.getCliente().getCodigo());
                    statement.setInt(5, venda.getCaixa().getCodigo());
                    statement.setString(6, venda.getVendedor().getNome());
                    statement.executeUpdate();
                    ResultSet results = statement.getGeneratedKeys();
                    results.first();
                    int key = ConnectionFactory.getKeyTable(results);
                    venda.setCodigo(key);
                    flag = true;
                } catch (SQLException e) {
                    System.err.println("ERRO (REGISTER SALE): " + e.getMessage());
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Venda> queryAllSales() {
        List<Venda> vendas = new ArrayList<Venda>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                try {
                    String sql = "SELECT * FROM " + ConnectionFactory.database + ".venda ORDER BY VENDA_DATA;";
                    PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                    vendas = getSalesList(statement.executeQuery());
                } catch (SQLException e) {
                    System.err.println("ERRO (QUERY ALL SALES): " + e.getMessage());
                }
                ConnectionFactory.closeConnection();
            }
        }
        return vendas;
    }

    public static List<Venda> querySalesByDate(java.util.Date date) {
        List<Venda> vendas = new ArrayList<Venda>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                try {
                    String sql = "SELECT * FROM " + ConnectionFactory.database + ".venda WHERE VENDA_DATA = ? ORDER BY VENDA_DATA;";
                    PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                    statement.setDate(1, new Date(date.getTime()));
                    vendas = getSalesList(statement.executeQuery());
                } catch (SQLException e) {
                    System.err.println("ERRO (QUERY SALES BY DATE): " + e.getMessage());
                }
                ConnectionFactory.closeConnection();
            }
        }
        return vendas;
    }
}
