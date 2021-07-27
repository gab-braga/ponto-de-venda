package model;

public class Item {
    private int codigo;
    private int codigoProduto;
    private int codigoVenda;
    private int quantidade;

    public Item() {
    }

    public Item(int codigoProduto, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.quantidade = quantidade;
    }

    public Item(int codigoProduto, int codigoVenda, int quantidade) {
        this(codigoProduto, quantidade);
        this.codigoVenda = codigoVenda;
    }

    public Item(int codigo, int codigoProduto, int codigoVenda, int quantidade) {
        this(codigoProduto, codigoVenda, quantidade);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
