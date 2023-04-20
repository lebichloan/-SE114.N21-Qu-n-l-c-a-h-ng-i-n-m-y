package com.example.se114n21.Model;

public class ChiTietNhapHang {
    private String MaSP;
    private String SoLuong;
    private Double ThanhTien;
    private String NgayNhap;

    public ChiTietNhapHang() {
    }

    public ChiTietNhapHang(String maSP, String soLuong, Double thanhTien, String ngayNhap) {
        MaSP = maSP;
        SoLuong = soLuong;
        ThanhTien = thanhTien;
        NgayNhap = ngayNhap;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public Double getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(Double thanhTien) {
        ThanhTien = thanhTien;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    @Override
    public String toString() {
        return "ChiTietNhapHang{" +
                "MaSP='" + MaSP + '\'' +
                ", SoLuong='" + SoLuong + '\'' +
                ", ThanhTien=" + ThanhTien +
                ", NgayNhap='" + NgayNhap + '\'' +
                '}';
    }
}
