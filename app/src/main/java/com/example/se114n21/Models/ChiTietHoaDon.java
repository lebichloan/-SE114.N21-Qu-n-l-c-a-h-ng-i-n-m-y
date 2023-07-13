package com.example.se114n21.Models;

public class ChiTietHoaDon {
    private SanPham sanPham;
    private Integer SoLuong;
    private Integer ThanhTien;
    private Integer TienVon;

    public ChiTietHoaDon() {
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }

    public Integer getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(Integer thanhTien) {
        ThanhTien = thanhTien;
    }

    public Integer getTienVon() {
        return TienVon;
    }

    public void setTienVon(Integer tienVon) {
        TienVon = tienVon;
    }

    public ChiTietHoaDon(SanPham sanPham, Integer soLuong) {
        this.sanPham = sanPham;
        SoLuong = soLuong;
        ThanhTien = sanPham.getGiaBan() * soLuong;
        TienVon = sanPham.getGiaNhap() * soLuong;
    }
}
