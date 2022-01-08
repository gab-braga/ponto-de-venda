package model;

import java.util.Date;
import java.util.List;

public class Venda {

    private Integer codigo;

    private Double valor;

    private Date dataHora;

    private Cliente cliente;

    private Caixa caixa;

    private Usuario operator;

    private List<Item> items;

    public Venda() {
    }

    public Venda(List<Item> items) {
        this.items = items;
    }

    public Venda(double valor, Date dataHora, Cliente cliente, Caixa caixa, Usuario operator) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.cliente = cliente;
        this.caixa = caixa;
        this.operator = operator;
    }

    public Venda(int codigo, double valor, Date dataHora, Cliente cliente, Caixa caixa, Usuario operator) {
        this(valor, dataHora, cliente, caixa, operator);
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
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

    public Usuario getOperator() {
        return operator;
    }

    public void setOperator(Usuario operator) {
        this.operator = operator;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItemToList(Item newItem) {
        for (Item item : items) {
            if (item.equals(newItem)) {
                item.increaseQuantity();
                newItem.setQuantidade(item.getQuantidade());
                return;
            }
        }
        newItem.setVenda(this);
        getItems().add(newItem);
    }

    public void modifyItemQuantity(Item modifiedItem) {
        for (Item item : items) {
            if (item.equals(modifiedItem)) {
                item.setQuantidade(modifiedItem.getQuantidade());
                return;
            }
        }
    }

    public void removeItemToList(Item removedItem) {
        getItems().remove(removedItem);
    }
}
