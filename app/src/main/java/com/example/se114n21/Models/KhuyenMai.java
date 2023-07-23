package com.example.se114n21.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KhuyenMai {
    private String MaKM;
    private String TenKM;
    private String MoTa;
    private String NgayBD;
    private String NgayKT;
    private int DonToiThieu;
    private double KhuyenMai;
    private int GiamToiDa;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKM, String tenKM, String moTa, String ngayBD, String ngayKT, int donToiThieu, double khuyenMai, int giamToiDa) {
        MaKM = maKM;
        TenKM = tenKM;
        MoTa = moTa;
        NgayBD = ngayBD;
        NgayKT = ngayKT;
        DonToiThieu = donToiThieu;
        KhuyenMai = khuyenMai;
        GiamToiDa = giamToiDa;
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

    public int getDonToiThieu() {
        return DonToiThieu;
    }

    public void setDonToiThieu(int donToiThieu) {
        DonToiThieu = donToiThieu;
    }

    public double getKhuyenMai() {
        return KhuyenMai;
    }

    public void setKhuyenMai(double khuyenMai) {
        KhuyenMai = khuyenMai;
    }

    public int getGiamToiDa() {
        return GiamToiDa;
    }

    public void setGiamToiDa(int giamToiDa) {
        GiamToiDa = giamToiDa;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String,Object> result = new HashMap<>();
        result.put("tenKM", TenKM);
        result.put("moTa",MoTa);
        result.put("ngayBD",NgayBD);
        result.put("ngayKT",NgayKT);
        result.put("donToiThieu",DonToiThieu);
        result.put("khuyenMai",KhuyenMai);
        result.put("giamToiDa",GiamToiDa);
        return result;
    }

}
