package model.dao;

import model.Product;
import model.Stock;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StockDAO extends DAO<Stock> {
    public StockDAO() {
        super(Stock.class);
    }

    public List<Stock> selectAllStock() {
        List<Stock> stock = new ArrayList<>();
        TypedQuery<Stock> query = entityManager.createNamedQuery("selectAllStock", specifiedClass);
        stock = query.getResultList();
        return stock;
    }

    public List<Stock> selectStockByCode(Long code) {
        List<Stock> stock = new ArrayList<>();
        TypedQuery<Stock> query = entityManager.createNamedQuery("selectStockByCode", specifiedClass);
        query.setParameter("code", code);
        stock = query.getResultList();
        return stock;
    }

    public List<Stock> selectStockByDescription(String description) {
        List<Stock> stock = new ArrayList<>();
        TypedQuery<Stock> query = entityManager.createNamedQuery("selectStockByDescription", specifiedClass);
        query.setParameter("description", "%" + description + "%");
        stock = query.getResultList();
        return stock;
    }

    public List<Stock> selectStockByCodeOrDescription(Long code, String description) {
        List<Stock> stock = new ArrayList<>();
        TypedQuery<Stock> query = entityManager.createNamedQuery("selectStockByCodeOrDescription", specifiedClass);
        query.setParameter("code", code);
        query.setParameter("description", "%" + description + "%");
        stock = query.getResultList();
        return stock;
    }

    public List<Stock> selectStockByProductCode(Product product) {
        List<Stock> stock = new ArrayList<>();
        TypedQuery<Stock> query = entityManager.createNamedQuery("selectStockByProductCode", specifiedClass);
        query.setParameter("product", product);
        stock = query.getResultList();
        return stock;
    }

    public boolean addToStock(Stock stock, int quantity) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Stock> query = entityManager.createNamedQuery("addToStock", specifiedClass);
            query.setParameter("code", stock.getCode());
            query.setParameter("quantity", quantity);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public boolean withdrawStock(Stock stock, int quantity) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Stock> query = entityManager.createNamedQuery("withdrawStock", specifiedClass);
            query.setParameter("code", stock.getCode());
            query.setParameter("quantity", quantity);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public boolean adjustStock(Stock stock) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Stock> query = entityManager.createNamedQuery("adjustStock", specifiedClass);
            query.setParameter("code", stock.getCode());
            query.setParameter("quantity", stock.getQuantity());
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
            TypedQuery<Stock> query = entityManager.createNamedQuery("deleteStock", specifiedClass);
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
}
