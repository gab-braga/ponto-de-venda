package model;

public class Estoque {

    private int codigo;

    private Produto produto;

    private String tipoEmbalado;

    private int quantidade;

    public Estoque(Produto produto, int quantidade) {
        this.produto = produto;
        this.tipoEmbalado = tipoEmbalado;
        this.quantidade = quantidade;
    }

    public Estoque(Produto produto, String tipoEmbalado, int quantidade) {
        this.produto = produto;
        this.tipoEmbalado = tipoEmbalado;
        this.quantidade = quantidade;
    }

    public Estoque(Produto produto, int codigo, String tipoEmbalado, int quantidade) {
        this(produto, tipoEmbalado, quantidade);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipoEmbalado() {
        return tipoEmbalado;
    }

    public void setTipoEmbalado(String tipoEmbalado) {
        this.tipoEmbalado = tipoEmbalado;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
