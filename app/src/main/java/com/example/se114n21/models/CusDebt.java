package com.example.se114n21.models;

public class CusDebt {
    String name;
    String id;
    Integer total;



    public CusDebt() {
    }

    public CusDebt(String name, String id, Integer total) {
        this.name = name;
        this.id = id;
        this.total = total;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
