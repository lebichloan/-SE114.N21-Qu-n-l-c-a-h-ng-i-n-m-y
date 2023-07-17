package com.example.se114n21.Models;

import java.io.Serializable;
import java.util.List;
public class NhanVien {
    private String LinkAvt;
    private String MaNV;
    private String HoTen;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String loaiNhanVien;

    public NhanVien() {
    }

    public NhanVien(String linkAvt, String maNV, String hoTen, String ngaySinh, String gioiTinh, String diaChi, String SDT, String email, String loaiNhanVien) {
        LinkAvt = linkAvt;
        MaNV = maNV;
        HoTen = hoTen;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        DiaChi = diaChi;
        this.SDT = SDT;
        Email = email;
        this.loaiNhanVien = loaiNhanVien;
    }

    public String getLinkAvt() {
        return LinkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        LinkAvt = linkAvt;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
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

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLoaiNhanVien() {
        return loaiNhanVien;
    }

    public void setLoaiNhanVien(String loaiNhanVien) {
        this.loaiNhanVien = loaiNhanVien;
    }
}
