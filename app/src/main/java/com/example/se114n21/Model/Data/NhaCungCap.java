package com.example.se114n21.Model.Data;

public class NhaCungCap {
    private String MaNhaCC;
    private String Ten;
    private String DiaChi;
    private String Email;
    private String DienThoai;

    public NhaCungCap() {
    }

    public NhaCungCap(String maNhaCC, String ten, String diaChi, String email, String dienThoai) {
        MaNhaCC = maNhaCC;
        Ten = ten;
        DiaChi = diaChi;
        Email = email;
        DienThoai = dienThoai;
    }

    public String getMaNhaCC() {
        return MaNhaCC;
    }

    public void setMaNhaCC(String maNhaCC) {
        MaNhaCC = maNhaCC;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }

    @Override
    public String toString() {
        return "NhaCC{" +
                "MaNhaCC='" + MaNhaCC + '\'' +
                ", Ten='" + Ten + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", Email='" + Email + '\'' +
                ", DienThoai='" + DienThoai + '\'' +
                '}';
    }
}
