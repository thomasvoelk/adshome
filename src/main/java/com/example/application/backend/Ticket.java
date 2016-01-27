package com.example.application.backend;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JoinColumn(name = "UNIT_ID")
    @NotNull(message = "Unit is required")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnitData unit;

    @Column(name = "DETAILS")
    @NotNull(message = "details is required")
    private String details;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UnitData getUnit() {
        return unit;
    }

    public void setUnit(UnitData unit) {
        this.unit = unit;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket unitData = (Ticket) o;

        return id == unitData.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
