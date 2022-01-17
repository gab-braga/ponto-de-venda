package model.dao;

import model.Acquisition;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcquisitionDAO extends DAO<Acquisition> {

    public AcquisitionDAO() {
        super(Acquisition.class);
    }

    public List<Acquisition> selectAllAcquisitions() {
        List<Acquisition> acquisitions = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectAllAcquisitions", specifiedClass);
        acquisitions = query.getResultList();
        return acquisitions;
    }

    public List<Acquisition> selectAcquisitionByDate(Date date) {
        List<Acquisition> acquisitions = new ArrayList<>();
        TypedQuery query = entityManager.createNamedQuery("selectAcquisitionByDate", specifiedClass);
        query.setParameter("date", date);
        acquisitions = query.getResultList();
        return acquisitions;
    }
}
