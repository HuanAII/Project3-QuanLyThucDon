package com.example.models;
import java.time.LocalDate;

public class KhuyenMai {
    private String proId;
    private double discount;
    private String maGiamGia;
    private LocalDate startDate;
    private LocalDate endDate;

    public KhuyenMai() {}

    public KhuyenMai(String proId, double discount, String maGiamGia, LocalDate startDate, LocalDate endDate) {
        this.proId = proId;
        this.discount = discount;
        this.maGiamGia = maGiamGia;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
