package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

public static boolean isProductInOrder(int orderId, String productId) {
    String sql = "SELECT COUNT(*) FROM chitietdonhang WHERE idDonHang = ? AND idMon = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        ps.setString(2, productId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
