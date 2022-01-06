package dao;

import controller.util.Helper;
import model.Caixa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CaixaDAO {

    private static List<Caixa> getBoxList(ResultSet results) throws SQLException {
        List<Caixa> caixas = new ArrayList<Caixa>();
        while (results.next()) {
            Double valueDeposit = results.getDouble("CAIXA_VALOR_ENTRADA_TOTAL");
            Double valueOut = results.getDouble("CAIXA_VALOR_SAIDA_TOTAL");
            Date date = results.getDate("CAIXA_DATA");
            caixas.add(new Caixa(valueDeposit, valueOut, date));
        }
        return caixas;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql =
                                "CREATE TABLE IF NOT EXISTS caixa(" +
                                        "CAIXA_CODIGO INT AUTO_INCREMENT," +
                                        "CAIXA_VALOR_ENTRADA DOUBLE DEFAULT 0.0," +
                                        "CAIXA_VALOR_SAIDA DOUBLE DEFAULT 0.0," +
                                        "CAIXA_DATA DATE NOT NULL," +
                                        "PRIMARY KEY(CAIXA_CODIGO)" +
                                        ")" +
                                        "ENGINE=InnoDB;";
                        Statement statement = ConnectionFactory.connection.createStatement();
                        statement.execute(sql);
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (CREATE TABLE BOX): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static boolean register(Caixa caixa) {
        boolean flag = false;
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "INSERT INTO caixa (CAIXA_VALOR_ENTRADA, CAIXA_VALOR_SAIDA, CAIXA_DATA) VALUES (?, ?, ?);";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                        statement.setDouble(1, caixa.getValorEntrada());
                        statement.setDouble(2, caixa.getValorSaida());
                        statement.setDate(3, new Date(caixa.getData().getTime()));
                        statement.executeUpdate();
                        ResultSet results = statement.getGeneratedKeys();
                        results.first();
                        int key = ConnectionFactory.getKeyTable(results);
                        caixa.setCodigo(key);
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (REGISTER BOX): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Caixa> queryBoxByCode(int codigo) {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA FROM caixa WHERE CAIXA_CODIGO = ? ORDER BY CAIXA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, codigo);
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BOX BY CODE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    public static List<Caixa> queryAllDailyBoxs() {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA FROM caixa GROUP BY CAIXA_DATA ORDER BY CAIXA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL DAILY BOXS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    public static List<Caixa> queryAllMonthlyBoxs() {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA, MONTH(CAIXA_DATA) AS MONTH, YEAR(CAIXA_DATA) AS YEAR FROM caixa GROUP BY MONTH, YEAR ORDER BY MONTH, YEAR;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL MONTHLY BOXS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    public static List<Caixa> queryAllYearlyBoxs() {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA, YEAR(CAIXA_DATA) AS YEAR FROM caixa GROUP BY YEAR ORDER BY YEAR;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL YEARLY BOXS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    public static List<Caixa> queryBoxByDate(java.util.Date date) {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA FROM caixa WHERE CAIXA_DATA = ? GROUP BY CAIXA_DATA ORDER BY CAIXA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setDate(1, new Date(date.getTime()));
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BOX BY DATE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    public static List<Caixa> queryBoxByMonthYear(java.util.Date date) {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA, MONTH(CAIXA_DATA) AS MONTH, YEAR(CAIXA_DATA) AS YEAR FROM caixa WHERE MONTH(CAIXA_DATA) = ? AND YEAR(CAIXA_DATA) = ? GROUP BY MONTH, YEAR ORDER BY MONTH, YEAR;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, Helper.extractMonthFromDate(date));
                        statement.setString(2, Helper.extractYearFromDate(date));
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BOX BY MONTH AND YEAR): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    public static List<Caixa> queryBoxByYear(java.util.Date date) {
        List<Caixa> caixas = new ArrayList<Caixa>();
        if (createTable()) {
            if (ConnectionFactory.openConnection()) {
                if (ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT SUM(CAIXA_VALOR_ENTRADA) AS CAIXA_VALOR_ENTRADA_TOTAL, SUM(CAIXA_VALOR_SAIDA) AS CAIXA_VALOR_SAIDA_TOTAL, CAIXA_DATA, YEAR(CAIXA_DATA) AS YEAR FROM caixa WHERE YEAR(CAIXA_DATA) = ? GROUP BY YEAR ORDER BY YEAR;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setString(1, Helper.extractYearFromDate(date));
                        caixas = getBoxList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY BOX BY YEAR): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return caixas;
    }

    protected static Caixa getBoxByCode(int codigo) {
        List<Caixa> caixas = queryBoxByCode(codigo);
        Caixa caixa = null;
        if (caixas.size() > 0) {
            caixa = queryBoxByCode(codigo).get(0);
        }
        return caixa;
    }
}
