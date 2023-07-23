package com.example.se114n21.models;

public class Sale {
    String date;
    Integer order;
    Integer total;

    public Sale() {
    }

    public Sale(String date, Integer order, Integer total) {
        this.date = date;
        this.order = order;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
