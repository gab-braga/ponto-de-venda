package model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales")
@DiscriminatorValue(value = "S")
public class Sale extends Operation {

    @OneToOne
    @JoinColumn(name = "CLIENT", nullable = true)
    private Client client;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
    private List<Item> items;

    public Sale() {
    }

    public Sale(List<Item> items) {
        this.items = items;
    }

    public Sale(Double valor, User operator, Date dataHora, Client client) {
        setValue(valor);
        setOperator(operator);
        setDate(dataHora);
        this.client = client;
    }

    public Sale(Long codigo, Double valor, User operator, Date dataHora, Client client) {
        this(valor, operator, dataHora, client);
        setCode(codigo);
    }

    public Client getCliente() {
        return client;
    }

    public void setCliente(Client client) {
        this.client = client;
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
                newItem.setQuantity(item.getQuantity());
                return;
            }
        }
        newItem.setVenda(this);
        getItems().add(newItem);
    }

    public void modifyItemQuantity(Item modifiedItem) {
        for (Item item : items) {
            if (item.equals(modifiedItem)) {
                item.setQuantity(modifiedItem.getQuantity());
                return;
            }
        }
    }

    public void removeItemToList(Item removedItem) {
        getItems().remove(removedItem);
    }
}
