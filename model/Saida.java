package model;

import java.util.Date;

public class Saida {

    private int codigo;

    private double valor;

    private Date dataHora;

    private String motivo;

    private Caixa caixa;

    private Usuario operador;

    public Saida(double valor, Date dataHora, String motivo, Caixa caixa, Usuario operador) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.motivo = motivo;
        this.caixa = caixa;
        this.operador = operador;
    }

    public Saida(int codigo, double valor, Date dataHora, String motivo, Caixa caixa, Usuario operador) {
        this(valor, dataHora, motivo, caixa, operador);
        this.codigo = codigo;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Usuario getOperador() {
        return operador;
    }

    public void setOperador(Usuario operador) {
        this.operador = operador;
    }
}
