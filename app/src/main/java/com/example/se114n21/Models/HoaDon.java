package com.example.se114n21.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoaDon {
    private String MaHD;
    private String MaNV;
    private String MaKH;
    private String NgayHD;
    private String DiaCHiNhanHang;
    private String DienThoaiNhanHang;
    private Integer PhiVanChuyen;
    private Integer PhiLapDat;
    private Integer ChietKhau;
    private Integer TongTienPhaiTra;
    private Integer TienVon;
    private String GhiChu;
    private String PhuongThucThanhToan;
    private List<ChiTietHoaDon> ChiTietHD;

    public HoaDon() {
        this.ChiTietHD = new ArrayList<>();
    }

    public HoaDon(String maHD, String maNV, String maKH, String ngayHD, String diaCHiNhanHang, String dienThoaiNhanHang, Integer phiVanChuyen, Integer phiLapDat, Integer chietKhau, Integer tongTienPhaiTra, Integer tienVon, String ghiChu, String phuongThucThanhToan, List<ChiTietHoaDon> chiTietHD) {
        MaHD = maHD;
        MaNV = maNV;
        MaKH = maKH;
        NgayHD = ngayHD;
        DiaCHiNhanHang = diaCHiNhanHang;
        DienThoaiNhanHang = dienThoaiNhanHang;
        PhiVanChuyen = phiVanChuyen;
        PhiLapDat = phiLapDat;
        ChietKhau = chietKhau;
        TongTienPhaiTra = tongTienPhaiTra;
        TienVon = tienVon;
        GhiChu = ghiChu;
        PhuongThucThanhToan = phuongThucThanhToan;
        ChiTietHD = chiTietHD;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getNgayHD() {
        return NgayHD;
    }

    public void setNgayHD(String ngayHD) {
        NgayHD = ngayHD;
    }

    public String getDiaCHiNhanHang() {
        return DiaCHiNhanHang;
    }

    public void setDiaCHiNhanHang(String diaCHiNhanHang) {
        DiaCHiNhanHang = diaCHiNhanHang;
    }

    public String getDienThoaiNhanHang() {
        return DienThoaiNhanHang;
    }

    public void setDienThoaiNhanHang(String dienThoaiNhanHang) {
        DienThoaiNhanHang = dienThoaiNhanHang;
    }

    public Integer getPhiVanChuyen() {
        return PhiVanChuyen;
    }

    public void setPhiVanChuyen(Integer phiVanChuyen) {
        PhiVanChuyen = phiVanChuyen;
    }

    public Integer getPhiLapDat() {
        return PhiLapDat;
    }

    public void setPhiLapDat(Integer phiLapDat) {
        PhiLapDat = phiLapDat;
    }

    public Integer getChietKhau() {
        return ChietKhau;
    }

    public void setChietKhau(Integer chietKhau) {
        ChietKhau = chietKhau;
    }

    public Integer getTongTienPhaiTra() {
        return TongTienPhaiTra;
    }

    public void setTongTienPhaiTra(Integer tongTienPhaiTra) {
        TongTienPhaiTra = tongTienPhaiTra;
    }

    public Integer getTienVon() {
        return TienVon;
    }

    public void setTienVon(Integer tienVon) {
        TienVon = tienVon;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getPhuongThucThanhToan() {
        return PhuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        PhuongThucThanhToan = phuongThucThanhToan;
    }

    public List<ChiTietHoaDon> getChiTietHD() {
        return ChiTietHD;
    }

    public void setChiTietHD(List<ChiTietHoaDon> chiTietHD) {
        this.ChiTietHD.addAll(chiTietHD);
    }
}
