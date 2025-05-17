package com.example.models;

public class Table {
    private String idTable;
    private int tableNumber;
    private int seats;


    public Table() {}

    public Table(String idTable, int tableNumber, int seats) {
        this.idTable = idTable;
        this.tableNumber = tableNumber;
        this.seats = seats;
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
}
