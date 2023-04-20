package com.example.se114n21.Model;

public class KhachHang {
    private String MaKH;
    private String Ten;
    private String DiaChi;
    private String DienThoai;
    private String Email;
    private String LoaiKH;

    public KhachHang() {
    }

    public KhachHang(String maKH, String ten, String diaChi, String dienThoai, String email, String loaiKH) {
        MaKH = maKH;
        Ten = ten;
        DiaChi = diaChi;
        DienThoai = dienThoai;
        Email = email;
        LoaiKH = loaiKH;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String maKH) {
        MaKH = maKH;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLoaiKH() {
        return LoaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        LoaiKH = loaiKH;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "MaKH='" + MaKH + '\'' +
                ", Ten='" + Ten + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", DienThoai='" + DienThoai + '\'' +
                ", Email='" + Email + '\'' +
                ", LoaiKH='" + LoaiKH + '\'' +
                '}';
    }
}
