package com.example.se114n21.Models;

public class ChuCuaHang {
    private String LinkAvt;
    private String HoTen;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String SDT;
    private String Email;

    public ChuCuaHang() {
    }

    public ChuCuaHang(String linkAvt, String hoten, String ngaySinh, String gioiTinh, String diaChi, String sdt, String email) {
        LinkAvt = linkAvt;
        HoTen = hoten;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        DiaChi = diaChi;
        SDT = sdt;
        Email = email;
    }

    public String getLinkAvt() {
        return LinkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        LinkAvt = linkAvt;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoten) {
        HoTen = hoten;
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

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String sdt) {
        SDT = sdt;
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
                ", HoTen='" + HoTen + '\'' +
                ", NgaySinh='" + NgaySinh + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", SDT='" + SDT + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
