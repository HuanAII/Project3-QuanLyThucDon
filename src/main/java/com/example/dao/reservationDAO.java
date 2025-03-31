package com.example.dao;
import java.sql.Statement;
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
            stmt.setDate(4, java.sql.Date.valueOf(res.getDate())); // Định dạng đúng cho SQL DATE
            stmt.setString(5, res.getTime());
            stmt.setString(6, res.getMessage());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; 
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = DBConnection.getConnection();
            if (conn == null) return tables;
    
            String sql = "SELECT * FROM ban_an";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id_table = rs.getInt("id_table");
                int table_number = rs.getInt("table_number");
                String status = rs.getString("trang_thai");
                int seats = rs.getInt("so_cho_ngoi");
    
                Table table = new Table(id_table, table_number, seats, status);
                tables.add(table); 
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tables;
    }
    
}
