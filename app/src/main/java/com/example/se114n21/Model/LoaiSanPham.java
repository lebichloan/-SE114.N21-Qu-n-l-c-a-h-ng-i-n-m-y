package com.example.se114n21.Model;

public class LoaiSanPham {
    private String MaLSP;
    private String TenLSP;

    public LoaiSanPham() {
    }

    public LoaiSanPham(String maLSP, String tenLSP) {
        MaLSP = maLSP;
        TenLSP = tenLSP;
    }

    public String getMaLSP() {
        return MaLSP;
    }

    public void setMaLSP(String maLSP) {
        MaLSP = maLSP;
    }

    public String getTenLSP() {
        return TenLSP;
    }

    public void setTenLSP(String tenLSP) {
        TenLSP = tenLSP;
    }

    @Override
    public String toString() {
        return "LoaiSanPham{" +
                "MaLSP='" + MaLSP + '\'' +
                ", TenLSP='" + TenLSP + '\'' +
                '}';
    }
}
