package com.example.se114n21.Models;

import java.util.List;
public class NhanVien {
    private String LinkAvt;
    private String MaNV;
    private String Ten;
    private String NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String DienThoai;
    private String Email;
    private String NgayVaoLam;
    private Double LuongTheoGio;
    private List<String> DanhSachCaLam;

    public NhanVien() {
    }

    public NhanVien(String linkAvt, String maNV, String ten, String ngaySinh, String gioiTinh, String diaChi, String dienThoai, String email, String ngayVaoLam, Double luongTheoGio, List<String> danhSachCaLam) {
        LinkAvt = linkAvt;
        MaNV = maNV;
        Ten = ten;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        DiaChi = diaChi;
        DienThoai = dienThoai;
        Email = email;
        NgayVaoLam = ngayVaoLam;
        LuongTheoGio = luongTheoGio;
        DanhSachCaLam = danhSachCaLam;
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

    public String getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(String ngayVaoLam) {
        NgayVaoLam = ngayVaoLam;
    }

    public Double getLuongTheoGio() {
        return LuongTheoGio;
    }

    public void setLuongTheoGio(Double luongTheoGio) {
        LuongTheoGio = luongTheoGio;
    }

    public List<String> getDanhSachCaLam() {
        return DanhSachCaLam;
    }

    public void setDanhSachCaLam(List<String> danhSachCaLam) {
        DanhSachCaLam = danhSachCaLam;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "LinkAvt='" + LinkAvt + '\'' +
                ", MaNV='" + MaNV + '\'' +
                ", Ten='" + Ten + '\'' +
                ", NgaySinh='" + NgaySinh + '\'' +
                ", GioiTinh='" + GioiTinh + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", DienThoai='" + DienThoai + '\'' +
                ", Email='" + Email + '\'' +
                ", NgayVaoLam='" + NgayVaoLam + '\'' +
                ", LuongTheoGio=" + LuongTheoGio +
                ", DanhSachCaLam=" + DanhSachCaLam +
                '}';
    }
}
