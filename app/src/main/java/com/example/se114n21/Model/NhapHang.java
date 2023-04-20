package com.example.se114n21.Model;

import java.util.List;

public class NhapHang {
    private String MaDonNhapHang;
    private String NgayNhap;
    private String MaNhaCungCap;
    private Double TongTien;
    private String TinhTrang;
    private String TenNguoiNhap;
    private String MaNV;

    private List<ChiTietNhapHang> ChiTietNH;

    public NhapHang() {
    }

    public NhapHang(String maDonNhapHang, String ngayNhap, String maNhaCungCap, Double tongTien, String tinhTrang, String tenNguoiNhap, String maNV, List<ChiTietNhapHang> chiTietNH) {
        MaDonNhapHang = maDonNhapHang;
        NgayNhap = ngayNhap;
        MaNhaCungCap = maNhaCungCap;
        TongTien = tongTien;
        TinhTrang = tinhTrang;
        TenNguoiNhap = tenNguoiNhap;
        MaNV = maNV;
        ChiTietNH = chiTietNH;
    }

    public String getMaDonNhapHang() {
        return MaDonNhapHang;
    }

    public void setMaDonNhapHang(String maDonNhapHang) {
        MaDonNhapHang = maDonNhapHang;
    }

    public String getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        NgayNhap = ngayNhap;
    }

    public String getMaNhaCungCap() {
        return MaNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        MaNhaCungCap = maNhaCungCap;
    }

    public Double getTongTien() {
        return TongTien;
    }

    public void setTongTien(Double tongTien) {
        TongTien = tongTien;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getTenNguoiNhap() {
        return TenNguoiNhap;
    }

    public void setTenNguoiNhap(String tenNguoiNhap) {
        TenNguoiNhap = tenNguoiNhap;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public List<ChiTietNhapHang> getChiTietNH() {
        return ChiTietNH;
    }

    public void setChiTietNH(List<ChiTietNhapHang> chiTietNH) {
        ChiTietNH = chiTietNH;
    }

    @Override
    public String toString() {
        return "NhapHang{" +
                "MaDonNhapHang='" + MaDonNhapHang + '\'' +
                ", NgayNhap='" + NgayNhap + '\'' +
                ", MaNhaCungCap='" + MaNhaCungCap + '\'' +
                ", TongTien=" + TongTien +
                ", TinhTrang='" + TinhTrang + '\'' +
                ", TenNguoiNhap='" + TenNguoiNhap + '\'' +
                ", MaNV='" + MaNV + '\'' +
                ", ChiTietNH=" + ChiTietNH +
                '}';
    }
}
