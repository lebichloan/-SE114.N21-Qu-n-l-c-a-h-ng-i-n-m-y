package com.example.se114n21.Models;

public class ThuocTinh {
    private String TenTT;
    private String GiaTriTT;

    public ThuocTinh() {
    }

    public ThuocTinh(String tenTT, String giaTriTT) {
        TenTT = tenTT;
        GiaTriTT = giaTriTT;
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
