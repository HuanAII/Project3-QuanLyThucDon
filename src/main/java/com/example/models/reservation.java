package com.example.models;

public class reservation {
    private String name;
    private String phone;
    private int guests;
    private String date;
    private String time;
    private String message;

    public reservation(){}
    public reservation(String name, String phone, int guests, String date, String time, String message){
        this.name= name;
        this.phone=phone;
        this.guests=guests;
        this.date=date;
        this.time=time;
        this.message=message;
    }
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

}
