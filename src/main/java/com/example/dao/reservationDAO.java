package com.example.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.DBConnection;
import com.example.models.Table;
import com.example.models.reservation;

public class reservationDAO {

    public static boolean saveWaitingReservation(reservation res) throws SQLException {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                System.out.println("Ket noi database that bai");
                return false;
            }
    
            String sql = "{Call saveWaitingReservation(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(sql);
            stmt.setString(1, res.getName());
            stmt.setString(2, res.getPhone());
            stmt.setInt(3, res.getGuests());
            stmt.setDate(4, java.sql.Date.valueOf(res.getDate()));
    
            String timeString = res.getTime();
            if (timeString.length() == 5) { 
                timeString = timeString + ":00";  
            }
            stmt.setTime(5, java.sql.Time.valueOf(timeString)); 
            stmt.setString(6, res.getMessage());
    
            System.out.println("Name: " + res.getName());
            System.out.println("Phone: " + res.getPhone());
            System.out.println("Guests: " + res.getGuests());
            System.out.println("Date: " + res.getDate());
            System.out.println("Time: " + res.getTime());
            System.out.println("Message: " + res.getMessage());
    
            stmt.execute();
            return true;
    
        } catch (SQLException e) {
            System.err.println("Loi sql: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Loi gia tri null: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Loi : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return false;
    }
    

    public boolean saveReservation(reservation res) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;    
        try {
            conn = DBConnection.getConnection();
            if (conn == null) {
                return false;  
            }
            
            String sql = "INSERT INTO reservations (name, phone, guests, date, time, message) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, res.getName());
            stmt.setString(2, res.getPhone());
            stmt.setInt(3, res.getGuests());
            stmt.setDate(4, java.sql.Date.valueOf(res.getDate())); 
            stmt.setString(5, res.getTime());
            stmt.setString(6, res.getMessage());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; 
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(stmt, conn);
        }
    }

    public static List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            if (conn == null) return tables;

            stmt = conn.prepareCall("{CALL GetAllTables()}");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String idTable = rs.getObject("id_table") != null ? rs.getString("id_table") : "Unknown";
                int tableNumber = rs.getInt("table_number");
                String status = rs.getString("trang_thai") != null ? rs.getString("trang_thai") : "Unknown";
                int seats = rs.getInt("so_cho_ngoi");

                Table table = new Table(idTable, tableNumber, seats, status);
                tables.add(table); 
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách bàn: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }
        return tables;
    }

    
    private static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
