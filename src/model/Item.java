package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_CODE", nullable = false)
    private Long code;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SALE", nullable = false)
    private Sale sale;

    @OneToOne
    @JoinColumn(name = "PRODUCT", nullable = false)
    private Product product;

    public Item() {
    }

    public Item(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Item(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Item(Product product, Sale sale, Integer quantity) {
        this(product, quantity);
        this.sale = sale;
    }

    public Item(Long code, Product product, Sale sale, Integer quantity) {
        this(product, sale, quantity);
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Sale getVenda() {
        return sale;
    }

    public void setVenda(Sale sale) {
        this.sale = sale;
    }

    public Product getProduto() {
        return product;
    }

    public void setProduto(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity += 1;
    }

    public Double getTotalValue() {
        return getQuantity() * getProduto().getPrice();
    }

    @Override
    public String toString() {
        return String.format("%d | %s", getQuantity(), getProduto().getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if(o != null) {
            return ((Item) o).getProduto().getCode().equals(getProduto().getCode());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getQuantity(), getVenda(), getProduto());
    }
}
