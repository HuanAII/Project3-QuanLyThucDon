package com.example.dao;

import com.example.models.KhuyenMai;
import com.example.utils.DBConnection;  // Giả sử bạn có class này

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {

    // Create
public static void insert(KhuyenMai khuyenMai) throws Exception {
    String sql = "INSERT INTO khuyen_mai (discount, ma_giam_gia, start_date, end_date) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDouble(1, khuyenMai.getDiscount());
        ps.setString(2, khuyenMai.getMaGiamGia());

        // Chuyển LocalDate sang java.sql.Date
        ps.setDate(3, java.sql.Date.valueOf(khuyenMai.getStartDate()));
        ps.setDate(4, java.sql.Date.valueOf(khuyenMai.getEndDate()));

        ps.executeUpdate();
    }
}



    // Read all
    public static List<KhuyenMai> getAll() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT * FROM khuyen_mai";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai(
                    rs.getString("pro_id"),
                    rs.getDouble("discount"),
                    rs.getString("ma_giam_gia"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate()
                );
                list.add(km);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update
    public static boolean update(KhuyenMai km) {
        String sql = "UPDATE khuyen_mai SET discount = ?, ma_giam_gia = ?, start_date = ?, end_date = ? WHERE pro_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, km.getDiscount());
            stmt.setString(2, km.getMaGiamGia());
            stmt.setDate(3, Date.valueOf(km.getStartDate()));
            stmt.setDate(4, Date.valueOf(km.getEndDate()));
            stmt.setString(5, km.getProId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete
    public static boolean delete(String proId) {
        String sql = "DELETE FROM khuyen_mai WHERE pro_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find by ID
    public static KhuyenMai findById(String proId) {
        String sql = "SELECT * FROM khuyen_mai WHERE pro_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new KhuyenMai(
                        rs.getString("pro_id"),
                        rs.getDouble("discount"),
                        rs.getString("ma_giam_gia"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
