package com.example.se114n21.Models;

public class ThuocTinh {
    private String MaSP;
    private String TenTT;
    private String GiaTriTT;

    public ThuocTinh() {
    }

    public ThuocTinh(String maSP, String tenTT, String giaTriTT) {
        MaSP = maSP;
        TenTT = tenTT;
        GiaTriTT = giaTriTT;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenTT() {
        return TenTT;
    }

    public void setTenTT(String tenTT) {
        TenTT = tenTT;
    }

    public String getGiaTriTT() {
        return GiaTriTT;
    }

    public void setGiaTriTT(String giaTriTT) {
        GiaTriTT = giaTriTT;
    }
}
