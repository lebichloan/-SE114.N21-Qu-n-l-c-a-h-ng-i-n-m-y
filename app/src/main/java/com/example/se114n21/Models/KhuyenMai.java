package com.example.se114n21.Models;

import java.util.Date;

public class KhuyenMai {
    private String MaKM;
    private String TenKM;
    private String MoTa;
    private Double GiamGia;
    private String NgayBD;
    private String NgayKT;
    private Integer GiamToiDa;
    private Integer GiaTriToiThieu;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, String tenKM, String moTa, Double giamGia, String ngayBD, String ngayKT, Integer giamToiDa, Integer giaTriToiThieu) {
        MaKM = maKM;
        TenKM = tenKM;
        MoTa = moTa;
        GiamGia = giamGia;
        NgayBD = ngayBD;
        NgayKT = ngayKT;
        GiamToiDa = giamToiDa;
        GiaTriToiThieu = giaTriToiThieu;
    }

    public String getMaKM() {
        return MaKM;
    }

    public void setMaKM(String maKM) {
        MaKM = maKM;
    }

    public String getTenKM() {
        return TenKM;
    }

    public void setTenKM(String tenKM) {
        TenKM = tenKM;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public Double getGiamGia() {
        return GiamGia;
    }

    public void setGiamGia(Double giamGia) {
        GiamGia = giamGia;
    }

    public String getNgayBD() {
        return NgayBD;
    }

    public void setNgayBD(String ngayBD) {
        NgayBD = ngayBD;
    }

    public String getNgayKT() {
        return NgayKT;
    }

    public void setNgayKT(String ngayKT) {
        NgayKT = ngayKT;
    }

    public Integer getGiamToiDa() {
        return GiamToiDa;
    }

    public void setGiamToiDa(Integer giamToiDa) {
        GiamToiDa = giamToiDa;
    }

    public Integer getGiaTriToiThieu() {
        return GiaTriToiThieu;
    }

    public void setGiaTriToiThieu(Integer giaTriToiThieu) {
        GiaTriToiThieu = giaTriToiThieu;
    }

}
