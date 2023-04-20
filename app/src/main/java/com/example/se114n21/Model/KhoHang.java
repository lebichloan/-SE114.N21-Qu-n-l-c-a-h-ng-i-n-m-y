package com.example.se114n21.Model;

public class KhoHang {
    private String MaSP;
    private Long SoLuong;

    public KhoHang() {
    }

    public KhoHang(String maSP, Long soLuong) {
        MaSP = maSP;
        SoLuong = soLuong;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public Long getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Long soLuong) {
        SoLuong = soLuong;
    }

    @Override
    public String toString() {
        return "KhoHang{" +
                "MaSP='" + MaSP + '\'' +
                ", SoLuong=" + SoLuong +
                '}';
    }
}
