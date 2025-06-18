package com.example.models;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class DonHang {
    private int idDonHang;
    private Date date;
    private Time time;
    private double total;
    private String status;
    private String idTable;
    private int accountId;
    private String tenKH;
    private String sdt;
    private String diaChi;
    private List<ChiTietDonHang> chiTietList;
    public DonHang() {}
    
    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
    }

    public Date getDate() {
        return date;
    }


    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
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

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
}
