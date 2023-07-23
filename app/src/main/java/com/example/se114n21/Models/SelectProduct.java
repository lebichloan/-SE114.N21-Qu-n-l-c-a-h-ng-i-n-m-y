package com.example.se114n21.Models;

public class SelectProduct {
    private SanPham sanPham;
    private boolean isSeleted;

    public SelectProduct() {
    }

    public SelectProduct(SanPham sanPham, boolean isSeleted) {
        this.sanPham = sanPham;
        this.isSeleted = isSeleted;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setSeleted(boolean seleted) {
        isSeleted = seleted;
    }
}
