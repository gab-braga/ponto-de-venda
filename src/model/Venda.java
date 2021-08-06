package model;

import java.util.Date;

public class Venda {

    private int codigo;

    private double valor;

    private Date dataHora;

    private Cliente cliente;

    private Caixa caixa;

    private Usuario vendedor;

    public Venda(double valor, Date dataHora, Cliente cliente, Caixa caixa, Usuario vendedor) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.cliente = cliente;
        this.caixa = caixa;
        this.vendedor = vendedor;
    }

    public Venda(int codigo, double valor, Date dataHora, Cliente cliente, Caixa caixa, Usuario vendedor) {
        this(valor, dataHora, cliente, caixa, vendedor);
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }
}
