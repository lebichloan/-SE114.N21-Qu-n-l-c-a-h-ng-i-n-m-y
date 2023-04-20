package com.example.se114n21.Model;

import java.util.List;

public class CaLamViec {
    private String MaCL;
    private List<String> DanhSachNhanVien;
    private String NgayLam;
    private String BatDau;
    private String KetThuc;
    private Double SoGioLam;

    public CaLamViec() {
    }

    public CaLamViec(String maCL, List<String> danhSachNhanVien, String ngayLam, String batDau, String ketThuc, Double soGioLam) {
        MaCL = maCL;
        DanhSachNhanVien = danhSachNhanVien;
        NgayLam = ngayLam;
        BatDau = batDau;
        KetThuc = ketThuc;
        SoGioLam = soGioLam;
    }

    public String getMaCL() {
        return MaCL;
    }

    public void setMaCL(String maCL) {
        MaCL = maCL;
    }

    public List<String> getDanhSachNhanVien() {
        return DanhSachNhanVien;
    }

    public void setDanhSachNhanVien(List<String> danhSachNhanVien) {
        DanhSachNhanVien = danhSachNhanVien;
    }

    public String getNgayLam() {
        return NgayLam;
    }

    public void setNgayLam(String ngayLam) {
        NgayLam = ngayLam;
    }

    public String getBatDau() {
        return BatDau;
    }

    public void setBatDau(String batDau) {
        BatDau = batDau;
    }

    public String getKetThuc() {
        return KetThuc;
    }

    public void setKetThuc(String ketThuc) {
        KetThuc = ketThuc;
    }

    public Double getSoGioLam() {
        return SoGioLam;
    }

    public void setSoGioLam(Double soGioLam) {
        SoGioLam = soGioLam;
    }

    @Override
    public String toString() {
        return "CaLamViec{" +
                "MaCL='" + MaCL + '\'' +
                ", DanhSachNhanVien=" + DanhSachNhanVien +
                ", NgayLam='" + NgayLam + '\'' +
                ", BatDau='" + BatDau + '\'' +
                ", KetThuc='" + KetThuc + '\'' +
                ", SoGioLam=" + SoGioLam +
                '}';
    }
}
