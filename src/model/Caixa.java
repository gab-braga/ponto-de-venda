package model;

import java.util.Date;

public class Caixa {
    // NANUTEA DA OPERAÇÃO: 1 -> VENDA; 2 -> SAIDA

    private int codigo;
    private double valorEntrada;
    private double valorSaida;
    private double valorTotal;
    private Date data;

    public Caixa(double valorEntrada, double valorSaida, Date data) {
        this.valorEntrada = valorEntrada;
        this.valorSaida = valorSaida;
        this.data = data;
    }

    public Caixa(int codigo, double valorEntrada, double valorSaida, Date data) {
        this.codigo = codigo;
        this.valorEntrada = valorEntrada;
        this.valorSaida = valorSaida;
        this.data = data;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(double valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public double getValorSaida() {
        return this.valorSaida;
    }

    public void setValorSaida(double valorSaida) {
        this.valorSaida = valorSaida;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValorTotal() {
        return (valorEntrada - valorSaida);
    }
}
