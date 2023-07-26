package com.example.se114n21.models;

public class Fund {
    String date;
    Integer total;


    public Fund() {
    }

    public Fund(String date, Integer total) {
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
