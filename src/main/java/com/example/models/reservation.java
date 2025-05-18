package com.example.models;

public class reservation {
    private int id_reservation;
    private int idAccount;
    private String name;
    private String phone;
    private int guests;
    private String date;
    private String idTable;
    private String time;
    private String foods;
    private String message;

    public reservation(){}
    public reservation(int id_reservation, int idAccount,String name, String phone,String idTable, int guests, String date, String time, String foods,String message){
        this.id_reservation = id_reservation;
        this.idAccount=idAccount;
        this.name= name;
        this.phone=phone;
        this.idTable=idTable;
        this.guests=guests;
        this.date=date;
        this.time=time;
        this.foods=foods;
        this.message=message;
    }

    public reservation(int id_reservation, int idAccount,String name, String phone, int guests, String date, String time, String foods,String message){
        this.id_reservation = id_reservation;
        this.idAccount=idAccount;
        this.name= name;
        this.phone=phone;
        this.guests=guests;
        this.date=date;
        this.time=time;
        this.foods=foods;
        this.message=message;
    }

    public String getIdTable() {
        return idTable;
    }
    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public int getId_reservation() {
        return id_reservation;
    }
    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }
    public int getIdAccount() { return idAccount; }
    public void setIdAccount( int idAccount) { this.idAccount = idAccount; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }


    public String getFoods() { return foods; }
    public void setFoods(String foods) { this.foods = foods; }
}
