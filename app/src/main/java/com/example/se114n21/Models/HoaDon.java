package com.example.se114n21.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HoaDon {
    private String MaHD;
    private String MaNV;
    private String MaKH;
    private String TenKH;
    private String SoDienThoaiKH;

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String tenKH) {
        TenKH = tenKH;
    }

    public String getSoDienThoaiKH() {
        return SoDienThoaiKH;
    }

    public void setSoDienThoaiKH(String soDienThoaiKH) {
        SoDienThoaiKH = soDienThoaiKH;
    }

    private String NgayHD;
    private String DiaCHiNhanHang;
    private String DienThoaiNhanHang;
    private Integer PhiVanChuyen;
    private Integer PhiLapDat;
    private Integer ChietKhau;
    private Integer KhuyenMai;
    private Integer TongTienHang;
    private Integer TongTienPhaiTra;
    private Integer TienVon;
    private String GhiChu;
    private String PhuongThucThanhToan;
    private List<ChiTietHoaDon> ChiTietHD;

    public HoaDon() {
        this.ChiTietHD = new ArrayList<>();
    }

    public HoaDon(String maHD, String maNV, String maKH, String tenKH, String soDienThoaiKH, String ngayHD, String diaCHiNhanHang, String dienThoaiNhanHang, Integer phiVanChuyen, Integer phiLapDat, Integer chietKhau, Integer khuyenMai, Integer tongTienHang, Integer tongTienPhaiTra, Integer tienVon, String ghiChu, String phuongThucThanhToan, List<ChiTietHoaDon> chiTietHD) {
        MaHD = maHD;
        MaNV = maNV;
        MaKH = maKH;
        TenKH = tenKH;
        SoDienThoaiKH = soDienThoaiKH;
        NgayHD = ngayHD;
        DiaCHiNhanHang = diaCHiNhanHang;
        DienThoaiNhanHang = dienThoaiNhanHang;
        PhiVanChuyen = phiVanChuyen;
        PhiLapDat = phiLapDat;
        ChietKhau = chietKhau;
        KhuyenMai = khuyenMai;
        TongTienHang = tongTienHang;
        TongTienPhaiTra = tongTienPhaiTra;
        TienVon = tienVon;
        GhiChu = ghiChu;
        PhuongThucThanhToan = phuongThucThanhToan;
        ChiTietHD = chiTietHD;
    }

    public Integer getTongTienHang() {
        return TongTienHang;
    }

    public void setTongTienHang(Integer tongTienHang) {
        TongTienHang = tongTienHang;
    }

    public Integer getKhuyenMai() {
        return KhuyenMai;
    }

    public void setKhuyenMai(Integer khuyenMai) {
        KhuyenMai = khuyenMai;
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
