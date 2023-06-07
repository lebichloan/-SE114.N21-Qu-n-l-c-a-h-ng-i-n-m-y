package com.example.se114n21.Models;

import java.util.ArrayList;
import java.util.List;

public class SanPham {
    private String MaSP;
    private String MaLSP;
    private List<String> LinkAnhSP;
    private String TenSP;
    private Integer GiaNhap;
    private Integer GiaBan;
    private String ThuongHieu;
    private Integer NamSX;
    private List<ThuocTinh> DSThuocTinh;
    private String Mota;
    private Double HoaHong;

    private Integer SoLuong;

    public SanPham() {
        LinkAnhSP = new ArrayList<>();
        DSThuocTinh = new ArrayList<>();
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer soLuong) {
        SoLuong = soLuong;
    }

    public SanPham(String maSP, String maLSP, List<String> linkAnhSP, String tenSP, Integer giaNhap, Integer giaBan, String thuongHieu, Integer namSX, List<ThuocTinh> DSThuocTinh, String mota, Double hoaHong, Integer soLuong) {
        MaSP = maSP;
        MaLSP = maLSP;
        LinkAnhSP = linkAnhSP;
        TenSP = tenSP;
        GiaNhap = giaNhap;
        GiaBan = giaBan;
        ThuongHieu = thuongHieu;
        NamSX = namSX;
        this.DSThuocTinh = DSThuocTinh;
        Mota = mota;
        HoaHong = hoaHong;
        SoLuong = soLuong;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(String maLSP) {
        MaLSP = maLSP;
    }

    public List<String> getLinkAnhSP() {
        return LinkAnhSP;
    }

    public void setLinkAnhSP(List<String> linkAnhSP) {
        LinkAnhSP.addAll(linkAnhSP);
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public Integer getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(Integer giaNhap) {
        GiaNhap = giaNhap;
    }

    public Integer getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(Integer giaBan) {
        GiaBan = giaBan;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        ThuongHieu = thuongHieu;
    }

    public Integer getNamSX() {
        return NamSX;
    }

    public void setNamSX(Integer namSX) {
        NamSX = namSX;
    }

    public List<ThuocTinh> getDSThuocTinh() {
        return DSThuocTinh;
    }

    public void setDSThuocTinh(List<ThuocTinh> DSThuocTinh) {
        this.DSThuocTinh.addAll(DSThuocTinh);
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public Double getHoaHong() {
        return HoaHong;
    }

    public void setHoaHong(Double hoaHong) {
        HoaHong = hoaHong;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "MaSP='" + MaSP + '\'' +
                ", MaLSP='" + MaLSP + '\'' +
                ", LinkAnhSP=" + LinkAnhSP +
                ", TenSP='" + TenSP + '\'' +
                ", GiaNhap=" + GiaNhap +
                ", GiaBan=" + GiaBan +
                ", ThuongHieu='" + ThuongHieu + '\'' +
                ", NamSX='" + NamSX + '\'' +
                ", DSThuocTinh=" + DSThuocTinh +
                ", Mota='" + Mota + '\'' +
                ", HoaHong=" + HoaHong +
                '}';
    }
}
