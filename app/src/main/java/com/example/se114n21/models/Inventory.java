package com.example.se114n21.models;

public class Inventory {
    String name;
    String id;
    Integer imp;
    Integer exp;


    public Inventory() {
    }

    public Inventory(String name, String id, Integer imp, Integer exp) {
        this.name = name;
        this.id = id;
        this.imp = imp;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getImp() {
        return imp;
    }

    public void setImp(Integer imp) {
        this.imp = imp;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }
}
