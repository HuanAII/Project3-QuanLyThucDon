package com.example.database;
import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("Ket noi thanh cong");
        } else {
            System.out.println("ket noi that bai!");
        }
    }
}