package dao;

import model.Cliente;
import model.Venda;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VendaDAO {

    public VendaDAO() {
        createTable();
    }

    private boolean createTable() {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql =
                    "CREATE TABLE IF NOT EXISTS venda(" +
                    "VENDA_CODIGO INT AUTO_INCREMENT PRIMARY KEY, " +
                    "VENDA_VALOR DOUBLE NOT NULL," +
                    "VENDA_DATA_HORA DATE NOT NULL," +
                    "VENDA_VENDEDOR VARCHAR(100) NOT NULL," +
                    "CLIENTE_CODIGO INT NOT NULL" +
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

    // INSERIR
    public static boolean register(Venda venda) {
        boolean flag = false;
        ConnectionFactory.openConnection();
        try {
            String sql = "INSERT INTO venda (VENDA_VALOR, VENDA_DATA_HORA, VENDA_VENDEDOR, CLIENTE_CODIGO) VALUES (?, ?, ?, ?);";
            PreparedStatement statement = ConnectionFactory.connection.prepareStatement(sql);
            statement.setDouble(1, venda.getValor());
            statement.setDate(2, (Date) venda.getDataHora());
            statement.setString(3, venda.getVedendor());
            statement.setInt(4, venda.getCliente().getCodigo());
            statement.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.err.println("ERRO (REGISTER SALE): " + e.getMessage());
        }
        ConnectionFactory.closeConnection();
        return flag;
    }
}
