package model;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STOCK_CODE")
    private Long code;

    @OneToOne
    @JoinColumn(name = "PRODUCT_CODE")
    private Product product;

    @Column(name = "UNITY")
    private String unity;

    @Column(name = "QUANTITY")
    private Integer quantity;

    public Stock() {
    }

    public Stock(Product product, Integer quantity) {
        this.product = product;
        this.unity = unity;
        this.quantity = quantity;
    }

    public Stock(Product product, String unity, Integer quantity) {
        this.product = product;
        this.unity = unity;
        this.quantity = quantity;
    }

    public Stock(Long code, Product product, String unity, Integer quantity) {
        this(product, unity, quantity);
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduto() {
        return product;
    }

    public void setProduto(Product product) {
        this.product = product;
    }
}
