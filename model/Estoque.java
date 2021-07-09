package model;

public class Estoque {
    private int codigo;
    private String tipoEmbalado;
    private int qntPorEmbalado;
    private int qntTotalEmUnidade;
    private Produto produto;

    public Estoque() {
    }

    public Estoque(String tipoEmbalado, int qntPorEmbalado, int qntTotalEmUnidade, Produto produto) {
        this.tipoEmbalado = tipoEmbalado;
        this.qntPorEmbalado = qntPorEmbalado;
        this.qntTotalEmUnidade = qntTotalEmUnidade;
        this.produto = produto;
    }

    public Estoque(int codigo, String tipoEmbalado, int qntPorEmbalado, int qntTotalEmUnidade, Produto produto) {
        this(tipoEmbalado, qntPorEmbalado, qntTotalEmUnidade, produto);
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

    public int getQntPorEmbalado() {
        return qntPorEmbalado;
    }

    public void setQntPorEmbalado(int qntPorEmbalado) {
        this.qntPorEmbalado = qntPorEmbalado;
    }

    public int getQntTotalEmUnidade() {
        return qntTotalEmUnidade;
    }

    public void setQntTotalEmUnidade(int qntTotalEmUnidade) {
        this.qntTotalEmUnidade = qntTotalEmUnidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
