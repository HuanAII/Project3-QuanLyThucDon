package com.example.models;

import java.util.List;

public class CartItem {
    private String idMon;
    private String tenMon;
    private String hinhAnh;
    private double gia;
    private int soLuong;
    
    public CartItem() {
    }
    
    public CartItem(String idMon, String tenMon, String hinhAnh, double gia, int soLuong) {
        this.idMon = idMon;
        this.tenMon = tenMon;
        this.hinhAnh = hinhAnh;
        this.gia = gia;
        this.soLuong = soLuong;
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
    public String getHinhAnh() {
        return hinhAnh;
    }
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    public double getGia() {
        return gia;
    }
    public void setGia(double gia) {
        this.gia = gia;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "CartItem [idMon=" + idMon + ", tenMon=" + tenMon + ", hinhAnh=" + hinhAnh + ", gia=" + gia
                + ", soLuong=" + soLuong + "]";
    }


    public double tinhTongTien(List<CartItem> cart) {
        double tongTien = 0.0;
        if (cart != null) {
            for (CartItem item : cart) {
                tongTien += item.getSoLuong() * item.getGia(); // Tính tổng tiền: số lượng * giá
            }
        }
        return tongTien;
    }
}

