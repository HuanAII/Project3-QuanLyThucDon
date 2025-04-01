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
            // Đóng tài nguyên
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
                // Kiểm tra NULL trước khi đọc dữ liệu
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
            // Đóng tài nguyên
            closeResources(rs, stmt, conn);
        }
        return tables;
    }

    // Hàm tiện ích để đóng tài nguyên JDBC
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
