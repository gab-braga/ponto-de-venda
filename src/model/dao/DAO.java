package model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO<E> {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    Class<E> specifiedClass;

    public DAO(Class<E> specifiedClass) {
        entityManagerFactory = Persistence.createEntityManagerFactory("persistence-pdv");
        entityManager = entityManagerFactory.createEntityManager();
        specifiedClass = specifiedClass;
    }

    public boolean insert(E object) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return false;
    }

    public boolean delete(E object) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(object);
            return true;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return false;
    }

    public boolean update(E object) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(object);
            return true;
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return false;
    }

    public E consult(E object) {
        try {
            entityManager.getTransaction().begin();
            return entityManager.find(specifiedClass, object);
        }
        catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }
}
