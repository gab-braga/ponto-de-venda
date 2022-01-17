package com.github.fgabrielbraga.model.dao;

import com.github.fgabrielbraga.model.Item;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAO<Item> {

    public ItemDAO() {
        super(Item.class);
    }

    public List<Item> selectItemBySaleCode(Long saleCode) {
        List<Item> acquisitions = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectItemBySaleCode", specifiedClass);
        query.setParameter("saleCode", saleCode);
        acquisitions = query.getResultList();
        return acquisitions;
    }
}
