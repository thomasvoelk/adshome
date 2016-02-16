package com.example.application.backend;

import org.springframework.beans.factory.annotation.*;

import javax.persistence.*;
import java.util.*;

public class UnitDataRepositoryImpl implements UnitDataRepositoryCustom {

    private EntityManager em;

    @Autowired
    public UnitDataRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<UnitData> xxx() {
        TypedQuery<UnitData> query = em.createQuery("select u from UnitData u order by u.unitName", UnitData.class);
        return query.getResultList();
    }
}
