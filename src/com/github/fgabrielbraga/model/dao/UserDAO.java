package com.github.fgabrielbraga.model.dao;

import com.github.fgabrielbraga.model.User;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public List<User> selectAllUsers() {
        List<User> products = new ArrayList<>();
        TypedQuery<User> query = entityManager.createNamedQuery("selectAllUsers", specifiedClass);
        products = query.getResultList();
        return products;
    }

    public List<User> selectUserByNameAndPassword(String name, String password) {
        List<User> products = new ArrayList<>();
        TypedQuery<User> query = entityManager.createNamedQuery("selectUserByNameAndPassword", specifiedClass);
        query.setParameter("name", name);
        query.setParameter("password", password);
        products = query.getResultList();
        return products;
    }

    public List<User> selectUserByName(String name) {
        List<User> products = new ArrayList<>();
        TypedQuery<User> query = entityManager.createNamedQuery("selectUserByName", specifiedClass);
        query.setParameter("name", name);
        products = query.getResultList();
        return products;
    }

    public List<User> selectUserByPermission(String permission) {
        List<User> products = new ArrayList<>();
        TypedQuery<User> query = entityManager.createNamedQuery("selectUserByPermission", specifiedClass);
        query.setParameter("permission", permission);
        products = query.getResultList();
        return products;
    }

    public List<User> selectUserByNameOrPermission(String name, String permission) {
        List<User> products = new ArrayList<>();
        TypedQuery<User> query = entityManager.createNamedQuery("selectUserByNameOrPermission", specifiedClass);
        query.setParameter("name", name);
        query.setParameter("permission", permission);
        products = query.getResultList();
        return products;
    }

    public boolean deleteByName(String name) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<User> query = entityManager.createNamedQuery("deleteUser", specifiedClass);
            query.setParameter("name", name);
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
