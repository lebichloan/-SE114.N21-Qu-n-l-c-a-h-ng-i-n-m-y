package com.example.se114n21.Model;

public class BangLuong {
    private String MaBL;
    private String MaNV;
    private String NgayLapBang;
    private Long Thang;
    private Long Nam;
    private Double TongGioLam;
    private Double LuongTheoGio;
    private Double TienGioLam;
    private Double TienHoaHong;
    private Double TienThuong;
    private Double LuongThucNhan;

    public BangLuong() {
    }

    public BangLuong(String maBL, String maNV, String ngayLapBang, Long thang, Long nam, Double tongGioLam, Double luongTheoGio, Double tienGioLam, Double tienHoaHong, Double tienThuong, Double luongThucNhan) {
        MaBL = maBL;
        MaNV = maNV;
        NgayLapBang = ngayLapBang;
        Thang = thang;
        Nam = nam;
        TongGioLam = tongGioLam;
        LuongTheoGio = luongTheoGio;
        TienGioLam = tienGioLam;
        TienHoaHong = tienHoaHong;
        TienThuong = tienThuong;
        LuongThucNhan = luongThucNhan;
    }

    public String getMaBL() {
        return MaBL;
    }

    public void setMaBL(String maBL) {
        MaBL = maBL;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getNgayLapBang() {
        return NgayLapBang;
    }

    public void setNgayLapBang(String ngayLapBang) {
        NgayLapBang = ngayLapBang;
    }

    public Long getThang() {
        return Thang;
    }

    public void setThang(Long thang) {
        Thang = thang;
    }

    public Long getNam() {
        return Nam;
    }

    public void setNam(Long nam) {
        Nam = nam;
    }

    public Double getTongGioLam() {
        return TongGioLam;
    }

    public void setTongGioLam(Double tongGioLam) {
        TongGioLam = tongGioLam;
    }

    public Double getLuongTheoGio() {
        return LuongTheoGio;
    }

    public void setLuongTheoGio(Double luongTheoGio) {
        LuongTheoGio = luongTheoGio;
    }

    public Double getTienGioLam() {
        return TienGioLam;
    }

    public void setTienGioLam(Double tienGioLam) {
        TienGioLam = tienGioLam;
    }

    public Double getTienHoaHong() {
        return TienHoaHong;
    }

    public void setTienHoaHong(Double tienHoaHong) {
        TienHoaHong = tienHoaHong;
    }

    public Double getTienThuong() {
        return TienThuong;
    }

    public void setTienThuong(Double tienThuong) {
        TienThuong = tienThuong;
    }

    public Double getLuongThucNhan() {
        return LuongThucNhan;
    }

    public void setLuongThucNhan(Double luongThucNhan) {
        LuongThucNhan = luongThucNhan;
    }

    @Override
    public String toString() {
        return "BangLuong{" +
                "MaBL='" + MaBL + '\'' +
                ", MaNV='" + MaNV + '\'' +
                ", NgayLapBang='" + NgayLapBang + '\'' +
                ", Thang=" + Thang +
                ", Nam=" + Nam +
                ", TongGioLam=" + TongGioLam +
                ", LuongTheoGio=" + LuongTheoGio +
                ", TienGioLam=" + TienGioLam +
                ", TienHoaHong=" + TienHoaHong +
                ", TienThuong=" + TienThuong +
                ", LuongThucNhan=" + LuongThucNhan +
                '}';
    }
}
