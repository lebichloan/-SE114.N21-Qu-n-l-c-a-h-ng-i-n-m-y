package com.example.se114n21.Models;

public class Account {
    private String Email;
    private String Password;
    private String MaND;
    private String PhanQuyen;
    private String KeyID;

    public Account(String email, String password, String maND, String phanQuyen, String keyID) {
        Email = email;
        Password = password;
        MaND = maND;
        PhanQuyen = phanQuyen;
        KeyID = keyID;
    }

    public Account() {
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

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        MaND = maND;
    }

    public String getPhanQuyen() {
        return PhanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        PhanQuyen = phanQuyen;
    }

    public String getKeyID() {
        return KeyID;
    }

    public void setKeyID(String keyID) {
        KeyID = keyID;
    }
}
