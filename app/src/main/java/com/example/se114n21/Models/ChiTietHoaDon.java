package com.example.se114n21.Models;

public class ChiTietHoaDon {
    private SanPham sanPham;
    private String SoLuong;
    private Double ThanhTien;
    private Double TienVon;
    private String NgayHD;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(SanPham sanPham, String soLuong, Double thanhTien, Double tienVon, String ngayHD) {
        this.sanPham = sanPham;
        SoLuong = soLuong;
        ThanhTien = thanhTien;
        TienVon = tienVon;
        NgayHD = ngayHD;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
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

    public Double getTienVon() {
        return TienVon;
    }

    public void setTienVon(Double tienVon) {
        TienVon = tienVon;
    }

    public String getNgayHD() {
        return NgayHD;
    }

    public void setNgayHD(String ngayHD) {
        NgayHD = ngayHD;
    }
}
