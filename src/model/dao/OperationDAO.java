package model.dao;

import model.Operation;
import model.Report;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationDAO extends DAO<Operation> {

    public OperationDAO() {
        super(Operation.class);
    }

    public List<Operation> selectOperationsPerDay() {
        List<Operation> operations = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectOperationsPerDay", Report.class);
        operations = query.getResultList();
        return operations;
    }

    public List<Operation> selectOperationsPerDayByDate(Date date) {
        List<Operation> operations = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectOperationsPerDayByDate", Report.class);
        query.setParameter("date", date);
        operations = query.getResultList();
        return operations;
    }

    public List<Operation> selectOperationsPerMonth() {
        List<Operation> operations = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectOperationsPerMonth", Report.class);
        operations = query.getResultList();
        return operations;
    }

    public List<Operation> selectOperationsPerMonthByMonth(Date date) {
        List<Operation> operations = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectOperationsPerMonthByMonth", Report.class);
        query.setParameter("date", date);
        operations = query.getResultList();
        return operations;
    }

    public List<Operation> selectOperationsPerYear() {
        List<Operation> operations = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectOperationsPerYear", Report.class);
        operations = query.getResultList();
        return operations;
    }

    public List<Operation> selectOperationsPerYearByYear(Date date) {
        List<Operation> operations = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectOperationsPerYearByYear", Report.class);
        query.setParameter("date", date);
        operations = query.getResultList();
        return operations;
    }
}
