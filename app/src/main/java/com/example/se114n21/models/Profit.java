package com.example.se114n21.models;

public class Profit {
    String date;
    Integer total;

    public Profit() {
    }

    public Profit(String date, Integer order, Integer total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
