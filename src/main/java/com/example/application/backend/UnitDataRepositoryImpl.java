package com.example.application.backend;

import org.springframework.data.jpa.repository.support.*;

import java.util.*;

import static com.example.application.backend.QUnitData.*;

public class UnitDataRepositoryImpl extends QueryDslRepositorySupport implements UnitDataRepositoryCustom {
//
//    private EntityManager em;
//
//    @Autowired
//    public UnitDataRepositoryImpl(EntityManager em) {
//        this.em = em;
//    }

    public UnitDataRepositoryImpl() {
        super(UnitData.class);
    }

    @Override
    public List<UnitData> xxx() {
        System.out.println("XXX" + unitData.unitName);
        return from(unitData).fetchAll().list(unitData);
    }
}
