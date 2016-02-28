package com.example.application.view.characteristics;

import java.util.*;

public class Characteristic {
    String guid;
    String value = "";

    public Characteristic() {
        guid = UUID.randomUUID().toString();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Characteristic that = (Characteristic) o;

        return guid.equals(that.guid);

    }

    @Override
    public int hashCode() {
        return guid.hashCode();
    }
}
