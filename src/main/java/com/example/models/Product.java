package com.example.models;

public class Product {
    private String idMon;
    private String tenMon;
    private String idDanhMuc;
    private int gia;
    private String hinhAnh;
    private String mota;
    private String donViTinh;
    public Product() {
    }

    public Product(String idMon, String tenMon, String idDanhMuc, int gia, String hinhAnh, String mota, String donViTinh) {
        this.idMon = idMon;
        this.tenMon = tenMon;
        this.idDanhMuc = idDanhMuc;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
        this.mota = mota;
        this.donViTinh = donViTinh;
    }

    public String getIdMon() {
        return idMon;
    }

    public void setIdMon(String idMon) {
        this.idMon = idMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idMon='" + idMon + '\'' +
                ", tenMon='" + tenMon + '\'' +
                ", idDanhMuc='" + idDanhMuc + '\'' +
                ", gia=" + gia +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", mota='" + mota + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                '}';
    }
}
