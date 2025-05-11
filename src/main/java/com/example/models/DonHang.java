package com.example.models;

import java.sql.Date;
import java.util.List;

public class DonHang {
    private int idDonHang;
    private Date date;
    private double total;
    private String status;
    private String tenKH;
    private String sdt;
    private String diaChi;
    private List<ChiTietDonHang> chiTietList;

    // Getters, setters
    public int getIdDonHang() {
        return idDonHang;
    }
    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTenKH() {
        return tenKH;
    }
    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public List<ChiTietDonHang> getChiTietList() {
        return chiTietList;
    }
    public void setChiTietList(List<ChiTietDonHang> chiTietList) {
        this.chiTietList = chiTietList;
    }
    public DonHang(int idDonHang, Date date, double total, String status, String tenKH, String sdt, String diaChi,
                   List<ChiTietDonHang> chiTietList) {
        this.idDonHang = idDonHang;
        this.date = date;
        this.total = total;
        this.status = status;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.chiTietList = chiTietList;
    }
    public DonHang() {
        // Constructor mặc định
    }
    @Override
    public String toString() {
        return "DonHang{" +
                "idDonHang=" + idDonHang +
                ", date=" + date +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", tenKH='" + tenKH + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", chiTietList=" + chiTietList +
                '}';
    }
}

