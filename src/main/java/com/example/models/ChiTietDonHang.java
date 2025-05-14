package com.example.models;

public class ChiTietDonHang {
    private String tenMon;
    private int soLuong;
    private double gia;

    public String getTenMon() {
        return tenMon;
    }
    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public double getGia() {
        return gia;
    }
    public void setGia(double gia) {
        this.gia = gia;
    }
    public ChiTietDonHang(String tenMon, int soLuong, double gia) {
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.gia = gia;
    }
    public ChiTietDonHang() {
        // Constructor mặc định
    }
    @Override
    public String toString() {
        return "ChiTietDonHang{" +
                "tenMon='" + tenMon + '\'' +
                ", soLuong=" + soLuong +
                ", gia=" + gia +
                '}';
    }
}

