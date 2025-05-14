package com.example.models;

import java.util.List;

public class CartItem {
    private String idMon;
    private String tenMon;
    private String hinhAnh;
    private int gia;
    private int soLuong;
    
    public CartItem() {
    }
    
    public CartItem(String idMon, String tenMon, String hinhAnh, int gia, int soLuong) {
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
    public int getGia() {
        return gia;
    }
    public void setGia(int gia) {
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


    public int tinhTongTien(List<CartItem> cart) {
        int tongTien = 0;
        if (cart != null) {
            for (CartItem item : cart) {
                tongTien += item.getSoLuong() * item.getGia(); 
            }
        }
        return tongTien;
    }
}

