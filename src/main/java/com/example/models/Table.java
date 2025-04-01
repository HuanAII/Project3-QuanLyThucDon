package com.example.models;

public class Table {
    private String idTable;
    private int tableNumber;
    private int seats;
    private String status; 

    public Table() {}

    public Table(String idTable, int tableNumber, int seats, String status) {
        this.idTable = idTable;
        this.tableNumber = tableNumber;
        this.seats = seats;
        this.status = status;
    }

    // Getter và Setter
    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return "available".equalsIgnoreCase(this.status);
    }
}
