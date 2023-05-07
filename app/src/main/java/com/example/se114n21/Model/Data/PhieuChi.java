package com.example.se114n21.Model.Data;

public class PhieuChi {
    private String MaPC;
    private String NgayChi;
    private String NhomDoiTuong;
//    KhachHang, NhanVien, NhaCungCap, DoiTuongKhac
    private KhachHang KH;
    private NhanVien NV;
    private NhaCungCap MaNhaCC;
    private String DoiTuongKhac;
//
    private String NoiDung;
    private Double SoTienChi;
    private String PhuongThucChi;

    public PhieuChi() {
    }

    public PhieuChi(String maPC, String ngayChi, String nhomDoiTuong, KhachHang KH, NhanVien NV, NhaCungCap maNhaCC, String doiTuongKhac, String noiDung, Double soTienChi, String phuongThucChi) {
        MaPC = maPC;
        NgayChi = ngayChi;
        NhomDoiTuong = nhomDoiTuong;
        this.KH = KH;
        this.NV = NV;
        MaNhaCC = maNhaCC;
        DoiTuongKhac = doiTuongKhac;
        NoiDung = noiDung;
        SoTienChi = soTienChi;
        PhuongThucChi = phuongThucChi;
    }

    public String getMaPC() {
        return MaPC;
    }

    public void setMaPC(String maPC) {
        MaPC = maPC;
    }

    public String getNgayChi() {
        return NgayChi;
    }

    public void setNgayChi(String ngayChi) {
        NgayChi = ngayChi;
    }

    public String getNhomDoiTuong() {
        return NhomDoiTuong;
    }

    public void setNhomDoiTuong(String nhomDoiTuong) {
        NhomDoiTuong = nhomDoiTuong;
    }

    public KhachHang getKH() {
        return KH;
    }

    public void setKH(KhachHang KH) {
        this.KH = KH;
    }

    public NhanVien getNV() {
        return NV;
    }

    public void setNV(NhanVien NV) {
        this.NV = NV;
    }

    public NhaCungCap getMaNhaCC() {
        return MaNhaCC;
    }

    public void setMaNhaCC(NhaCungCap maNhaCC) {
        MaNhaCC = maNhaCC;
    }

    public String getDoiTuongKhac() {
        return DoiTuongKhac;
    }

    public void setDoiTuongKhac(String doiTuongKhac) {
        DoiTuongKhac = doiTuongKhac;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public Double getSoTienChi() {
        return SoTienChi;
    }

    public void setSoTienChi(Double soTienChi) {
        SoTienChi = soTienChi;
    }

    public String getPhuongThucChi() {
        return PhuongThucChi;
    }

    public void setPhuongThucChi(String phuongThucChi) {
        PhuongThucChi = phuongThucChi;
    }

    @Override
    public String toString() {
        return "PhieuChi{" +
                "MaPC='" + MaPC + '\'' +
                ", NgayChi='" + NgayChi + '\'' +
                ", NhomDoiTuong='" + NhomDoiTuong + '\'' +
                ", KH=" + KH +
                ", NV=" + NV +
                ", MaNhaCC=" + MaNhaCC +
                ", DoiTuongKhac='" + DoiTuongKhac + '\'' +
                ", NoiDung='" + NoiDung + '\'' +
                ", SoTienChi=" + SoTienChi +
                ", PhuongThucChi='" + PhuongThucChi + '\'' +
                '}';
    }
}
