package com.example.se114n21.Model.Data;

public class CuaHang {
    private String Ten;
    private String DiaChi;
    private String Hotline;
    private String Website;

    public CuaHang() {
    }

    public CuaHang(String ten, String diaChi, String hotline, String website) {
        Ten = ten;
        DiaChi = diaChi;
        Hotline = hotline;
        Website = website;
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

    public String getHotline() {
        return Hotline;
    }

    public void setHotline(String hotline) {
        Hotline = hotline;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    @Override
    public String toString() {
        return "CuaHang{" +
                "Ten='" + Ten + '\'' +
                ", DiaChi='" + DiaChi + '\'' +
                ", Hotline='" + Hotline + '\'' +
                ", Website='" + Website + '\'' +
                '}';
    }
}
