package model;

import java.util.Date;

public class Caixa {
    // NANUTEA DA OPERAÇÃO: 1 -> VENDA; 2 -> SAIDA

    private int codigo;
    private double valor;
    private Date dataHora;
    private int natureza;

    public Caixa(double valor, Date dataHora, int natureza) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.natureza = natureza;
    }

    public Caixa(int codigo, double valor, Date dataHora, int natureza) {
        this(valor, dataHora, natureza);
        this.natureza = natureza;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public int getNatureza() {
        return natureza;
    }

    public void setNatureza(int natureza) {
        this.natureza = natureza;
    }
}
