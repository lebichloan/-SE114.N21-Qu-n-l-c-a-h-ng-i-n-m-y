package com.example.se114n21.Models;

public class Account {
    private String Email;
    private String Password;
    private String PhanQuyen;
    private String MaNV;

    public Account() {
    }

    public Account(String email, String password, String phanQuyen, String maNV) {
        Email = email;
        Password = password;
        PhanQuyen = phanQuyen;
        MaNV = maNV;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        PhanQuyen = phanQuyen;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    @Override
    public String toString() {
        return "Account{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", PhanQuyen='" + PhanQuyen + '\'' +
                ", MaNV='" + MaNV + '\'' +
                '}';
    }
}
