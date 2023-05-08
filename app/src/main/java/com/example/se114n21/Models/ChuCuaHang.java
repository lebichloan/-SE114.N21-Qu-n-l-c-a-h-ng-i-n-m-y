package com.example.se114n21.Models;

public class ChuCuaHang {
    private String LinkAvt;
    private String Ten;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String DienThoai;
    private String Email;

    public ChuCuaHang() {
    }

    public ChuCuaHang(String linkAvt, String ten, String ngaySinh, String gioiTinh, String diaChi, String dienThoai, String email) {
        LinkAvt = linkAvt;
        Ten = ten;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        DiaChi = diaChi;
        DienThoai = dienThoai;
        Email = email;
    }

    public String getLinkAvt() {
        return LinkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        LinkAvt = linkAvt;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "ChuCuaHang{" +
                "LinkAvt='" + LinkAvt + '\'' +
                ", Ten='" + Ten + '\'' +
                ", NgaySinh='" + NgaySinh + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", DienThoai='" + DienThoai + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
