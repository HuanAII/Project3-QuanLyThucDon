package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.utils.DBConnection;

public class DetailOrderDAO {
        public static boolean addOrderDetails(int idDonHang, String idMon, int soLuong) {
        String sql = "INSERT INTO chitietdonhang (idDonHang, idMon, soLuong) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDonHang);
            ps.setString(2, idMon);
            ps.setInt(3, soLuong);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrderDetailsByOrderId(int idDonHang) {
    
    String sql = "DELETE FROM chitietdonhang WHERE idDonHang = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idDonHang);
        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;

    } catch (Exception e) {
        e.printStackTrace(); 
        return false;
    }
}


}
