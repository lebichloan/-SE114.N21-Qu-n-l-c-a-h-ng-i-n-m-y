package com.example.se114n21.Models;

public class DonNhapHang {
    private String MaDNH;
    private String MaNV;
    private String NgayDNH;
    private String MaSP;
    private String TenSP;
    private Integer SoLuongNhap;
    private Integer SoLuongTraHang;

    public DonNhapHang() {
    }

    public DonNhapHang(String maDNH, String maNV, String ngayDNH, String maSP, String tenSP, Integer soLuongNhap, Integer soLuongTraHang) {
        MaDNH = maDNH;
        MaNV = maNV;
        NgayDNH = ngayDNH;
        MaSP = maSP;
        TenSP = tenSP;
        SoLuongNhap = soLuongNhap;
        SoLuongTraHang = soLuongTraHang;
    }

    public String getMaDNH() {
        return MaDNH;
    }

    public void setMaDNH(String maDNH) {
        MaDNH = maDNH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getNgayDNH() {
        return NgayDNH;
    }

    public void setNgayDNH(String ngayDNH) {
        NgayDNH = ngayDNH;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public Integer getSoLuongNhap() {
        return SoLuongNhap;
    }

    public void setSoLuongNhap(Integer soLuongNhap) {
        SoLuongNhap = soLuongNhap;
    }

    public Integer getSoLuongTraHang() {
        return SoLuongTraHang;
    }

    public void setSoLuongTraHang(Integer soLuongTraHang) {
        SoLuongTraHang = soLuongTraHang;
    }
}
