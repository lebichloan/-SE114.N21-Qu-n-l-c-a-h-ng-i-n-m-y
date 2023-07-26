package com.example.se114n21.models;

public class Stock {
    String name;
    String id;
    Integer total;
    Integer amount;
    Integer cost;

    public Stock() {
    }

    public Stock(String name, String id, Integer total, Integer amount, Integer cost) {
        this.name = name;
        this.id = id;
        this.total = total;
        this.amount = amount;
        this.cost = cost;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
