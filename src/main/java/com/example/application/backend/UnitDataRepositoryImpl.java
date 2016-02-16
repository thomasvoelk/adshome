package com.example.application.backend;

import com.mysema.query.*;
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
        List<Tuple> a = from(unitData).groupBy(unitData.unitCode, unitData.unitName).where(unitData.unitName.startsWithIgnoreCase("a")).list(unitData.unitName, unitData.unitName.count());
        return from(unitData).fetchAll().list(unitData);
    }
}
