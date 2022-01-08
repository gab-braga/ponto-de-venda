package model;

import java.util.Objects;

public class Item {
    private Integer codigo;
    private Integer quantidade;
    private Venda venda;
    private Produto produto;

    public Item() {
    }

    public Item(Produto produto) {
        this.produto = produto;
        this.quantidade = 1;
    }

    public Item(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Item(Produto produto, Venda venda, Integer quantidade) {
        this(produto, quantidade);
        this.venda = venda;
    }

    public Item(int codigo, Produto produto, Venda venda, Integer quantidade) {
        this(produto, venda, quantidade);
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void increaseQuantity() {
        this.quantidade += 1;
    }

    public Double getTotalValue() {
        return getQuantidade() * getProduto().getValorVenda();
    }

    @Override
    public String toString() {
        return String.format("Descrição: %s | Quantidade: %d", getProduto().getDescricao(), getQuantidade());
    }

    @Override
    public boolean equals(Object o) {
        if(o != null) {
            return ((Item) o).getProduto().getCodigo().equals(getProduto().getCodigo());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getQuantidade(), getVenda(), getProduto());
    }
}
