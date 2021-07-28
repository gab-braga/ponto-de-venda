package dao;

import model.Caixa;
import model.Cliente;
import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CaixaDAO {

    protected static boolean createTable() {
        boolean flag = false;
        if(ConnectionFactory.createDatabase()) {
            if(ConnectionFactory.openConnection()) {
                try {
                    String sql =
                            "CREATE TABLE IF NOT EXISTS " + ConnectionFactory.database +".caixa(" +
                                    "CAIXA_CODIGO INT AUTO_INCREMENT," +
                                    "CAIXA_VALOR DOUBLE NOT NULL," +
                                    "CAIXA_DATA DATE NOT NULL," +
                                    "CAIXA_HORA TIME NOT NULL," +
                                    "CAIXA_NATUREZA INT NOT NULL," +
                                    "PRIMARY KEY(CAIXA_CODIGO)" +
                                    ")" +
                                    "ENGINE=InnoDB;";
                    Statement statement = ConnectionFactory.connection.createStatement();
                    statement.execute(sql);
                    flag = true;
                } catch (SQLException e) {
                    System.err.println("ERRO (CREATE TABLE BOX): " + e.getMessage());
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    private static List<Caixa> getBoxList(ResultSet results) throws SQLException {
        List<Caixa> caixas = new ArrayList<Caixa>();
        while (results.next()) {
            int code = results.getInt("CAIXA_CODIGO");
            Double value = results.getDouble("CAIXA_VALOR");
            Date date = results.getDate("CAIXA_DATA");
            Time hour = results.getTime("CAIXA_HORA");
            int type = results.getInt("CAIXA_NATUREZA");
            caixas.add(new Caixa(code, value, Datetime.getTimestamp(date, hour), type));
        }
        return caixas;
    }

    public static boolean register(Caixa caixa) {
        boolean flag = false;
        if(createTable()) {
            if(ConnectionFactory.openConnection()) {
                try {
                    String sql = "INSERT INTO " + ConnectionFactory.database + ".caixa (CAIXA_VALOR, CAIXA_DATA, CAIXA_HORA, CAIXA_NATUREZA) VALUES (?, ?, ?, ?);";
                    PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, caixa.getValor());
                    statement.setDate(2, new Date(caixa.getDataHora().getTime()));
                    statement.setTime(3, new Time(caixa.getDataHora().getTime()));
                    statement.setInt(4, caixa.getNatureza());
                    statement.executeUpdate();
                    ResultSet results = statement.getGeneratedKeys();
                    results.first();
                    int key = ConnectionFactory.getKeyTable(results);
                    caixa.setCodigo(key);
                    flag = true;
                } catch (SQLException e) {
                    System.err.println("ERRO (REGISTER BOX): " + e.getMessage());
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Caixa> queryBoxByCode(int codigo) {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                try {
                    String sql = "SELECT * FROM " + ConnectionFactory.database + ".caixa WHERE CAIXA_CODIGO = ? ORDER BY VENDA_DATA;";
                    PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                    statement.setInt(1, codigo);
                    caixas = getBoxList(statement.executeQuery());
                } catch (SQLException e) {
                    System.err.println("ERRO (QUERY BOX BY CODE): " + e.getMessage());
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }
}
