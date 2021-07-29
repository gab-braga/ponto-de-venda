package model;

public class Item {
    private int codigo;
    private int quantidade;
    private Venda venda;
    private Produto produto;

    public Item() {
    }

    public Item(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Item(Produto produto, Venda venda, int quantidade) {
        this(produto, quantidade);
        this.venda = venda;
    }

    public Item(int codigo, Produto produto, Venda venda, int quantidade) {
        this(produto, venda, quantidade);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
