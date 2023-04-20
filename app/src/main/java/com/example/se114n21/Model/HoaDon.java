package com.example.se114n21.Model;

import java.util.List;

public class HoaDon {
    private String MaHD;
    private String MaNV;
    private String MaKH;
    private String NgayHD;
    private String DiaCHiNhanHang;
    private String DienThoaiNhanHang;
    private Double PhiVanChuyen;
    private Double PhiLapDat;
    private Double ChietKhau;
    private Double TongTienPhaiTra;
    private Double TienVon;
    private String TinhTrangHD;
    private String TinhTrangGiaoHang;
    private String GhiChu;
    private String PhuongThucThanhToan;
    private List<ChiTietHoaDon> ChiTietHD;
    private Double HoaHong;

    public HoaDon() {
    }

    public HoaDon(String maHD, String maNV, String maKH, String ngayHD, String diaCHiNhanHang, String dienThoaiNhanHang, Double phiVanChuyen, Double phiLapDat, Double chietKhau, Double tongTienPhaiTra, Double tienVon, String tinhTrangHD, String tinhTrangGiaoHang, String ghiChu, String phuongThucThanhToan, List<ChiTietHoaDon> chiTietHD, Double hoaHong) {
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
        TinhTrangHD = tinhTrangHD;
        TinhTrangGiaoHang = tinhTrangGiaoHang;
        GhiChu = ghiChu;
        PhuongThucThanhToan = phuongThucThanhToan;
        ChiTietHD = chiTietHD;
        HoaHong = hoaHong;
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

    public Double getPhiVanChuyen() {
        return PhiVanChuyen;
    }

    public void setPhiVanChuyen(Double phiVanChuyen) {
        PhiVanChuyen = phiVanChuyen;
    }

    public Double getPhiLapDat() {
        return PhiLapDat;
    }

    public void setPhiLapDat(Double phiLapDat) {
        PhiLapDat = phiLapDat;
    }

    public Double getChietKhau() {
        return ChietKhau;
    }

    public void setChietKhau(Double chietKhau) {
        ChietKhau = chietKhau;
    }

    public Double getTongTienPhaiTra() {
        return TongTienPhaiTra;
    }

    public void setTongTienPhaiTra(Double tongTienPhaiTra) {
        TongTienPhaiTra = tongTienPhaiTra;
    }

    public Double getTienVon() {
        return TienVon;
    }

    public void setTienVon(Double tienVon) {
        TienVon = tienVon;
    }

    public String getTinhTrangHD() {
        return TinhTrangHD;
    }

    public void setTinhTrangHD(String tinhTrangHD) {
        TinhTrangHD = tinhTrangHD;
    }

    public String getTinhTrangGiaoHang() {
        return TinhTrangGiaoHang;
    }

    public void setTinhTrangGiaoHang(String tinhTrangGiaoHang) {
        TinhTrangGiaoHang = tinhTrangGiaoHang;
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
        ChiTietHD = chiTietHD;
    }

    public Double getHoaHong() {
        return HoaHong;
    }

    public void setHoaHong(Double hoaHong) {
        HoaHong = hoaHong;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "MaHD='" + MaHD + '\'' +
                ", MaNV='" + MaNV + '\'' +
                ", MaKH='" + MaKH + '\'' +
                ", NgayHD='" + NgayHD + '\'' +
                ", DiaCHiNhanHang='" + DiaCHiNhanHang + '\'' +
                ", DienThoaiNhanHang='" + DienThoaiNhanHang + '\'' +
                ", PhiVanChuyen=" + PhiVanChuyen +
                ", PhiLapDat=" + PhiLapDat +
                ", ChietKhau=" + ChietKhau +
                ", TongTienPhaiTra=" + TongTienPhaiTra +
                ", TienVon=" + TienVon +
                ", TinhTrangHD='" + TinhTrangHD + '\'' +
                ", TinhTrangGiaoHang='" + TinhTrangGiaoHang + '\'' +
                ", GhiChu='" + GhiChu + '\'' +
                ", PhuongThucThanhToan='" + PhuongThucThanhToan + '\'' +
                ", ChiTietHD=" + ChiTietHD +
                ", HoaHong=" + HoaHong +
                '}';
    }
}
