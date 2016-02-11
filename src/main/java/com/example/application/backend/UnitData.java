package com.example.application.backend;

import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.*;

@Entity
@BatchSize(size = 10)
public class UnitData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "UNIT_NAME")
    @NotNull(message = "Name is required")
    private String unitName;

    @Column(name = "UNIT_CODE")
    @NotNull(message = "Code is required")
    @Length(max = 3, min = 3)
    private String unitCode;

    @Column(name = "UNIT_FAVORITE")
    private Boolean favorite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnitData unitData = (UnitData) o;

        return id == unitData.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
