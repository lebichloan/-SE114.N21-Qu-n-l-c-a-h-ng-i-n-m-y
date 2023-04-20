package com.example.se114n21.Model;

import java.util.List;

public class SanPham {
    private String MaSP;
    private List<String> LinkAnhSP;
    private String TenSP;
    private Double GiaNhap;
    private Double GiaBan;
    private String ThuongHieu;
    private String NamSX;
    private List<ThuocTinh> DSThuocTinh;
    private String Mota;
    private Double HoaHong;

    public SanPham() {
    }

    public SanPham(String maSP, List<String> linkAnhSP, String tenSP, Double giaNhap, Double giaBan, String thuongHieu, String namSX, List<ThuocTinh> DSThuocTinh, String mota, Double hoaHong) {
        MaSP = maSP;
        LinkAnhSP = linkAnhSP;
        TenSP = tenSP;
        GiaNhap = giaNhap;
        GiaBan = giaBan;
        ThuongHieu = thuongHieu;
        NamSX = namSX;
        this.DSThuocTinh = DSThuocTinh;
        Mota = mota;
        HoaHong = hoaHong;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public List<String> getLinkAnhSP() {
        return LinkAnhSP;
    }

    public void setLinkAnhSP(List<String> linkAnhSP) {
        LinkAnhSP = linkAnhSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public Double getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(Double giaNhap) {
        GiaNhap = giaNhap;
    }

    public Double getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(Double giaBan) {
        GiaBan = giaBan;
    }

    public String getThuongHieu() {
        return ThuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        ThuongHieu = thuongHieu;
    }

    public String getNamSX() {
        return NamSX;
    }

    public void setNamSX(String namSX) {
        NamSX = namSX;
    }

    public List<ThuocTinh> getDSThuocTinh() {
        return DSThuocTinh;
    }

    public void setDSThuocTinh(List<ThuocTinh> DSThuocTinh) {
        this.DSThuocTinh = DSThuocTinh;
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
