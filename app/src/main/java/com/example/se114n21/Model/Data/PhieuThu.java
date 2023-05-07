package com.example.se114n21.Model.Data;

public class PhieuThu {
    private String MaPT;
    private String NgayThu;
    private String NhomDoiTuong;
//    KhachHang, NhanVien, NhaCungCap, DoiTuongKhac
    private KhachHang KH;
    private NhanVien NV;
    private NhaCungCap MaNhaCC;
    private String DoiTuongKhac;
//
    private String NoiDung;
    private Double SoTienThu;
    private String PhuongThucThu;

    public PhieuThu() {
    }

    public PhieuThu(String maPT, String ngayThu, String nhomDoiTuong, KhachHang KH, NhanVien NV, NhaCungCap maNhaCC, String doiTuongKhac, String noiDung, Double soTienThu, String phuongThucThu) {
        MaPT = maPT;
        NgayThu = ngayThu;
        NhomDoiTuong = nhomDoiTuong;
        this.KH = KH;
        this.NV = NV;
        MaNhaCC = maNhaCC;
        DoiTuongKhac = doiTuongKhac;
        NoiDung = noiDung;
        SoTienThu = soTienThu;
        PhuongThucThu = phuongThucThu;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String maPT) {
        MaPT = maPT;
    }

    public String getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(String ngayThu) {
        NgayThu = ngayThu;
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

    public Double getSoTienThu() {
        return SoTienThu;
    }

    public void setSoTienThu(Double soTienThu) {
        SoTienThu = soTienThu;
    }

    public String getPhuongThucThu() {
        return PhuongThucThu;
    }

    public void setPhuongThucThu(String phuongThucThu) {
        PhuongThucThu = phuongThucThu;
    }

    @Override
    public String toString() {
        return "PhieuThu{" +
                "MaPT='" + MaPT + '\'' +
                ", NgayThu='" + NgayThu + '\'' +
                ", NhomDoiTuong='" + NhomDoiTuong + '\'' +
                ", KH=" + KH +
                ", NV=" + NV +
                ", MaNhaCC=" + MaNhaCC +
                ", DoiTuongKhac='" + DoiTuongKhac + '\'' +
                ", NoiDung='" + NoiDung + '\'' +
                ", SoTienThu=" + SoTienThu +
                ", PhuongThucThu='" + PhuongThucThu + '\'' +
                '}';
    }
}
