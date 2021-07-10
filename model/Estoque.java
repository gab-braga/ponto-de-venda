package model;

import javax.persistence.*;

@Entity
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESTOQUE_CODIGO")
    private int codigo;

    @Column(name = "ESTOQUE_TIPO_DE_EMBALADO")
    private String tipoEmbalado;

    @Column(name = "ESTOQUE_QNT_POR_EMBALADO")
    private int qntPorEmbalado;

    @Column(name = "ESTOQUE_QNT_TOTAL_EM_UNIDADE")
    private int qntTotalEmUnidade;

    @Column(name = "ESTOQUE_CODIGO")
    private int codigoProduto;

    @Transient
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
