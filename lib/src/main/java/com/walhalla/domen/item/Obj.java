package com.walhalla.domen.item;


import java.io.Serializable;

public class Obj implements Serializable {

    private final String name;

    public Obj(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
