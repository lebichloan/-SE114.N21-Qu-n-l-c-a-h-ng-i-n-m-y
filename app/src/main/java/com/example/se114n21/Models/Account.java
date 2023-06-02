package com.example.se114n21.Models;

public class Account {
    private String LinkAvata;
    private String HoTen;
    private String Email;
    private String Password;
    private String PhanQuyen;

    public Account() {
    }

    public Account(String linkAvata, String hoTen,String email, String password, String phanQuyen) {
        LinkAvata = linkAvata;
        HoTen = hoTen;
        Email = email;
        Password = password;
        PhanQuyen = phanQuyen;
    }

    public String getLinkAvata() {
        return LinkAvata;
    }
    public String getHoTen() {
        return HoTen;
    }
    public String getEmail() {
        return Email;
    }
    public String getPassword() {
        return Password;
    }
    public String getPhanQuyen() {
        return PhanQuyen;
    }
    public void setLinkAvata(String linkAvata) {
        LinkAvata = linkAvata;
    }
    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public void setPhanQuyen(String phanQuyen) {
        PhanQuyen = phanQuyen;
    }
    @Override
    public String toString() {
        return "Account{" +
                "LinkAvata='" + LinkAvata + '\'' +
                "HoTen='" + HoTen + '\'' +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", PhanQuyen='" + PhanQuyen + '\'' +
                '}';
    }
}
