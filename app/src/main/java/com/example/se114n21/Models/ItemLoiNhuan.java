package com.example.se114n21.Models;

import java.util.ArrayList;
import java.util.List;

public class ItemLoiNhuan {
    private String Ngay;
    private Integer TongNgay;
    private Integer SoLuongNgay;
    private List<HoaDon> DanhSachHoaDon;

    public ItemLoiNhuan() {
        this.DanhSachHoaDon = new ArrayList<>();
        this.TongNgay = 0;
        this.SoLuongNgay = 0;
    }

    public ItemLoiNhuan(String ngay, Integer tongNgay, Integer soLuongNgay, List<HoaDon> danhSachHoaDon) {
        Ngay = ngay;
        TongNgay = tongNgay;
        SoLuongNgay = soLuongNgay;
        DanhSachHoaDon = danhSachHoaDon;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public Integer getTongNgay() {
        return TongNgay;
    }

    public void setTongNgay(Integer tongNgay) {
        TongNgay = tongNgay;
    }

    public Integer getSoLuongNgay() {
        return SoLuongNgay;
    }

    public void setSoLuongNgay(Integer soLuongNgay) {
        SoLuongNgay = soLuongNgay;
    }

    public List<HoaDon> getDanhSachHoaDon() {
        return DanhSachHoaDon;
    }

    public void setDanhSachHoaDon(List<HoaDon> danhSachHoaDon) {
        DanhSachHoaDon = danhSachHoaDon;
    }

    public void addHoaDon(HoaDon hoaDon) {
        this.DanhSachHoaDon.add(hoaDon);
        this.SoLuongNgay = this.SoLuongNgay + 1;
        this.TongNgay = this.TongNgay + hoaDon.getTongTienPhaiTra() - hoaDon.getTienVon();
    }
}
