package com.example.models;

import java.sql.Date;

public class HoaDon {
    private int idHoaDon;
    private int idDonHang;
    private String tenPhuongThucThanhToan;
    private Date ngayThanhToan;
    private double soTien;

    public HoaDon() {
    }
    public HoaDon(int idDonHang, String tenPhuongThucThanhToan, Date ngayThanhToan, double soTien) {
        this.idDonHang = idDonHang;
        this.tenPhuongThucThanhToan = tenPhuongThucThanhToan;
        this.ngayThanhToan = ngayThanhToan;
        this.soTien = soTien;
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }
    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }
    public int getIdDonHang() {
        return idDonHang;
    }
    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }
    public String getTenPhuongThucThanhToan() {
        return tenPhuongThucThanhToan;
    }
    public void setTenPhuongThucThanhToan(String tenPhuongThucThanhToan) {
        this.tenPhuongThucThanhToan = tenPhuongThucThanhToan;
    }
    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }
    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }
    public double getSoTien() {
        return soTien;
    }
    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }
    @Override
    public String toString() {
        return "HoaDon{" +
                "idHoaDon=" + idHoaDon +
                ", idDonHang=" + idDonHang +
                ", tenPhuongThucThanhToan='" + tenPhuongThucThanhToan + '\'' +
                ", ngayThanhToan=" + ngayThanhToan +
                ", soTien=" + soTien +
                '}';
    }
}

