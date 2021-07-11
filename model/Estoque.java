package model;

public class Estoque {

    private int codigo;

    private String tipoEmbalado;

    private int quantidade;

    private Produto produto;

    public Estoque() {
    }

    public Estoque(String tipoEmbalado, int quantidade, Produto produto) {
        this.tipoEmbalado = tipoEmbalado;
        this.produto = produto;
    }

    public Estoque(int codigo, String tipoEmbalado, int quantidade, Produto produto) {
        this(tipoEmbalado, quantidade, produto);
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
