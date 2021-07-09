package model;

import java.util.Date;

public class Venda {
    private int codigo;
    private double valor;
    private Date dataHora;
    private String vedendor;
    private Cliente cliente;

    public Venda() {
    }

    public Venda(double valor, Date dataHora, String vedendor, Cliente cliente) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.vedendor = vedendor;
        this.cliente = cliente;
    }

    public Venda(int codigo, double valor, Date dataHora, String vedendor, Cliente cliente) {
        this(valor, dataHora, vedendor, cliente);
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

    public String getVedendor() {
        return vedendor;
    }

    public void setVedendor(String vedendor) {
        this.vedendor = vedendor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
