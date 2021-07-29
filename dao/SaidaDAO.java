package dao;

import model.Caixa;
import model.Estoque;
import model.Saida;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SaidaDAO {

    private static List<Saida> getExitsList(ResultSet results) throws SQLException {
        List<Saida> saidas = new ArrayList<Saida>();
        while (results.next()) {
            int code = results.getInt("SAIDA_CODIGO");
            Date date = results.getDate("SAIDA_DATA");
            Time hour = results.getTime("SAIDA_HORA");
            Double value = results.getDouble("SAIDA_VALOR");
            String reason = results.getString("SAIDA_MOTIVO");
            int codeCaixa = results.getInt("CAIXA_CODIGO");
            String nameOperator = results.getString("USUARIO_NOME");
            saidas.add(new Saida(code, value, Datetime.getTimestamp(date, hour), reason, CaixaDAO.getBoxByCode(codeCaixa), UsuarioDAO.getUserByName(nameOperator)));
        }
        return saidas;
    }

    protected static boolean createTable() {
        boolean flag = false;
        if (ConnectionFactory.createDatabase()) {
            if (CaixaDAO.createTable() && UsuarioDAO.createTable()) {
                if (ConnectionFactory.openConnection()) {
                    if(ConnectionFactory.useDataBase()) {
                        try {
                            String sql =
                                    "CREATE TABLE IF NOT EXISTS saida(" +
                                            "SAIDA_CODIGO INT AUTO_INCREMENT," +
                                            "SAIDA_VALOR DOUBLE NOT NULL," +
                                            "SAIDA_DATA DATE NOT NULL," +
                                            "SAIDA_HORA TIME NOT NULL," +
                                            "SAIDA_MOTIVO TEXT NOT NULL," +
                                            "USUARIO_NOME VARCHAR(100) DEFAULT '-'," +
                                            "CAIXA_CODIGO INT NOT NULL," +
                                            "PRIMARY KEY (SAIDA_CODIGO)," +
                                            "FOREIGN KEY (CAIXA_CODIGO) REFERENCES caixa (CAIXA_CODIGO)," +
                                            "FOREIGN KEY (USUARIO_NOME) REFERENCES usuario (USUARIO_NOME)" +
                                            ")" +
                                            "ENGINE=InnoDB;";
                            Statement statement = ConnectionFactory.connection.createStatement();
                            statement.execute(sql);
                            flag = true;
                        } catch (SQLException e) {
                            System.err.println("ERRO (CREATE TABLE EXIT): " + e.getMessage());
                        }
                    }
                    ConnectionFactory.closeConnection();
                }
            }
        }
        return flag;
    }

    public static boolean register(Saida saida) {
        boolean flag = false;
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "INSERT INTO saida (SAIDA_VALOR, SAIDA_DATA, SAIDA_HORA, SAIDA_MOTIVO, CAIXA_CODIGO, USUARIO_NOME) VALUES (?, ?, ?, ?, ?, ?);";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setDouble(1, saida.getValor());
                        statement.setDate(2, new Date(saida.getDataHora().getTime()));
                        statement.setTime(3, new Time(saida.getDataHora().getTime()));
                        statement.setString(4, saida.getMotivo());
                        statement.setInt(5, saida.getCaixa().getCodigo());
                        statement.setString(6, saida.getOperador().getNome());
                        statement.executeUpdate();
                        flag = true;
                    } catch (SQLException e) {
                        System.err.println("ERRO (REGISTER EXIT): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return flag;
    }

    public static List<Saida> queryAllExits() {
        List<Saida> saidas = new ArrayList<Saida>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM saida ORDER BY SAIDA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        saidas = getExitsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY ALL EXITS): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return saidas;
    }

    public static List<Saida> queryExitsByDate(java.util.Date date) {
        List<Saida> saidas = new ArrayList<Saida>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM saida WHERE SAIDA_DATA = ? ORDER BY SAIDA_DATA;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setDate(1, new Date(date.getTime()));
                        saidas = getExitsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY EXITS BY DATE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return saidas;
    }

    public static List<Saida> queryExitByCode(int codigo) {
        List<Saida> saidas = new ArrayList<Saida>();
        if(createTable()) {
            if (ConnectionFactory.openConnection()) {
                if(ConnectionFactory.useDataBase()) {
                    try {
                        String sql = "SELECT * FROM saida WHERE SAIDA_CODIGO = ? ORDER BY SAIDA_CODIGO;";
                        PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
                        statement.setInt(1, codigo);
                        saidas = getExitsList(statement.executeQuery());
                    } catch (SQLException e) {
                        System.err.println("ERRO (QUERY EXIT BY CODE): " + e.getMessage());
                    }
                }
                ConnectionFactory.closeConnection();
            }
        }
        return saidas;
    }

    protected static Saida getExitByCode(int codigo) {
        return queryExitByCode(codigo).get(0);
    }
}
