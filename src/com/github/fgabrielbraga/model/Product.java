package com.github.fgabrielbraga.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "PRODUCT_CODE", nullable = false)
    private Long code;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    public Product() {
    }

    public Product(Long code, String description, Double price) {
        this.code = code;
        this.description = description;
        this.price = price;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s - %d", description, code);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        else if(((Product) o).getCode() == getCode()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getDescription(), getPrice());
    }
}
