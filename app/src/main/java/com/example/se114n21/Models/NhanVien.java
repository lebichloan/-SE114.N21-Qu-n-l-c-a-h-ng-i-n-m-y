package com.example.se114n21.Models;

import java.io.Serializable;
import java.util.List;
public class NhanVien {
    private String LinkAvt;
    private String MaNV;
    private String MaND;
    private String HoTen;
    private String NgaySinh;
    private String DiaChi;
    private String SDT;
    private String Email;
    private String loaiNhanVien;
    private String Password;
    private boolean TrangThai;

    public NhanVien() {
    }

    public NhanVien(String linkAvt, String maNV, String maND, String hoTen, String ngaySinh, String diaChi, String SDT, String email, String loaiNhanVien, String password, boolean trangThai) {
        LinkAvt = linkAvt;
        MaNV = maNV;
        MaND = maND;
        HoTen = hoTen;
        NgaySinh = ngaySinh;
        DiaChi = diaChi;
        this.SDT = SDT;
        Email = email;
        this.loaiNhanVien = loaiNhanVien;
        Password = password;
        TrangThai = trangThai;
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

    public String getMaND() {
        return MaND;
    }

    public void setMaND(String maND) {
        MaND = maND;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean trangThai) {
        TrangThai = trangThai;
    }
}
