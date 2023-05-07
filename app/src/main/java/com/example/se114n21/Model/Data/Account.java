package com.example.se114n21.Model.Data;

public class Account {
    private String DienThoai;
    private String Password;
    private String PhanQuyen;
    private String MaNV;

    public Account() {
    }

    public Account(String dienThoai, String password, String phanQuyen, String maNV) {
        DienThoai = dienThoai;
        Password = password;
        PhanQuyen = phanQuyen;
        MaNV = maNV;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
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
                "DienThoai='" + DienThoai + '\'' +
                ", Password='" + Password + '\'' +
                ", PhanQuyen='" + PhanQuyen + '\'' +
                ", MaNV='" + MaNV + '\'' +
                '}';
    }
}
