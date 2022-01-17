package com.github.fgabrielbraga.model.dao;

import com.github.fgabrielbraga.model.Client;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO extends DAO<Client> {

    public ClientDAO() {
        super(Client.class);
    }

    public List<Client> selectAllClients() {
        List<Client> clients = new ArrayList<>();
        TypedQuery<Client> query = entityManager.createNamedQuery("selectAllClients", specifiedClass);
        clients = query.getResultList();
        return clients;
    }

    public List<Client> selectClientByName(String name) {
        List<Client> clients = new ArrayList<>();
        TypedQuery<Client> query = entityManager.createNamedQuery("selectClientByName", specifiedClass);
        query.setParameter("name", "%" + name + "%");
        clients = query.getResultList();
        return clients;
    }

    public List<Client> selectClientByCPF(String cpf) {
        List<Client> clients = new ArrayList<>();
        TypedQuery<Client> query = entityManager.createNamedQuery("selectClientByCPF", specifiedClass);
        query.setParameter("cpf", cpf);
        clients = query.getResultList();
        return clients;
    }

    public List<Client> selectClientByNameOrCPF(String name, String cpf) {
        List<Client> clients = new ArrayList<>();
        TypedQuery<Client> query = entityManager.createNamedQuery("selectClientByNameOrCPF", specifiedClass);
        query.setParameter("name", "%" + name + "%");
        query.setParameter("cpf", cpf);
        clients = query.getResultList();
        return clients;
    }

    public List<Client> selectClientByCode(Long code) {
        List<Client> clients = new ArrayList<>();
        TypedQuery<Client> query = entityManager.createNamedQuery("selectClientByCode", specifiedClass);
        query.setParameter("code", code);
        clients = query.getResultList();
        return clients;
    }

    public boolean update(Client client) {
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Client> query = entityManager.createNamedQuery("updateClient", specifiedClass);
            query.setParameter("code", client.getCode());
            query.setParameter("name", client.getName());
            query.setParameter("cpf", client.getCpf());
            query.setParameter("phone", client.getPhone());
            query.setParameter("email", client.getEmail());
            query.setParameter("address", client.getAddress());
            query.setParameter("number", client.getNumber());
            query.setParameter("city", client.getCity());
            query.setParameter("uf", client.getUf());
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
            TypedQuery<Client> query = entityManager.createNamedQuery("deleteClient", specifiedClass);
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
