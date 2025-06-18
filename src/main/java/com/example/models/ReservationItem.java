package com.example.models;

public class ReservationItem {
    private int id;
    private int reservationId;
    private String monAnId;
    private String tenMon;
    private int soLuong;
    private int gia;

    public ReservationItem() {}

    public ReservationItem(int reservationId, String monAnId, String tenMon, int soLuong, int gia) {
        this.reservationId = reservationId;
        this.monAnId = monAnId;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public ReservationItem(int id, int reservationId, String  monAnId, String tenMon, int soLuong, int gia) {
        this.id = id;
        this.reservationId = reservationId;
        this.monAnId = monAnId;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getMonAnId() {
        return monAnId;
    }

    public void setMonAnId(String monAnId) {
        this.monAnId = monAnId;
    }

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

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
