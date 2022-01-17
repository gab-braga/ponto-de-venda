package model.dao;

import model.Sale;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaleDAO extends DAO<Sale> {

    public SaleDAO() {
        super(Sale.class);
    }

    public List<Sale> selectAllSales() {
        List<Sale> sales = new ArrayList<>();
        TypedQuery<Sale> query = entityManager.createNamedQuery("selectAllSales", specifiedClass);
        sales = query.getResultList();
        return sales;
    }

    public List<Sale> selectSalesByDate(Date date) {
        List<Sale> sales = new ArrayList<>();
        TypedQuery<Sale> query = entityManager.createNamedQuery("selectSalesByDate", specifiedClass);
        query.setParameter("date", date);
        sales = query.getResultList();
        return sales;
    }
}
