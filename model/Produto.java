package model;

public class Produto {
    private int codigo;
    private String descricao;
    private double valorVenda;

    public Produto() {
    }

    public Produto(int codigo, String descricao, double valorVenda) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valorVenda = valorVenda;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }
}
