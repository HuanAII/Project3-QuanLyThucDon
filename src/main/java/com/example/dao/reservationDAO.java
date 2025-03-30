package com.example.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.example.utils.DBConnection;
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
}
