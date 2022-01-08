package model;

import java.util.Objects;

public class Produto {

    private Integer codigo;

    private String descricao;

    private Double valorVenda;

    public Produto(int codigo, String descricao, double valorVenda) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.valorVenda = valorVenda;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(Double valorVenda) {
        this.valorVenda = valorVenda;
    }

    @Override
    public String toString() {
        return String.format("%s - %d", descricao, codigo);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        else if(((Produto) o).getCodigo() == getCodigo()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getDescricao(), getValorVenda());
    }
}
