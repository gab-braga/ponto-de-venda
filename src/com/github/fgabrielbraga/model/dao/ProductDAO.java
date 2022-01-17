package com.github.fgabrielbraga.model.dao;

import com.github.fgabrielbraga.model.Product;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DAO<Product> {

    public ProductDAO() {
        super(Product.class);
    }

    public boolean update(Product product) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Product> query = entityManager.createNamedQuery("updateProduct", specifiedClass);
            query.setParameter("description", product.getDescription());
            query.setParameter("price", product.getPrice());
            query.setParameter("code", product.getCode());
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public boolean deleteByCode(Long code) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Product> query = entityManager.createNamedQuery("deleteProduct", specifiedClass);
            query.setParameter("code", code);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        TypedQuery<Product> query = entityManager.createNamedQuery("selectAllProducts", specifiedClass);
        products = query.getResultList();
        return products;
    }

    public List<Product> selectProductByCode(Long code) {
        List<Product> products = new ArrayList<>();
        TypedQuery<Product> query = entityManager.createNamedQuery("selectProductByCode", specifiedClass);
        query.setParameter("code", code);
        products = query.getResultList();
        return products;
    }

    public List<Product> selectProductByDescription(String description) {
        List<Product> products = new ArrayList<>();
        TypedQuery<Product> query = entityManager.createNamedQuery("selectProductByDescription", specifiedClass);
        query.setParameter("description", "%" + description + "%");
        products = query.getResultList();
        return products;
    }

    public List<Product> selectProductByCodeOrDescription(Long code, String description) {
        List<Product> products = new ArrayList<>();
        TypedQuery<Product> query = entityManager.createNamedQuery("selectProductByCodeOrDescription", specifiedClass);
        query.setParameter("code", code);
        query.setParameter("description", "%" + description + "%");
        products = query.getResultList();
        return products;
    }
}
