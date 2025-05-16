package com.example.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import com.example.models.CartItem;

import com.example.utils.DBConnection;

public class CartDAO {
    public static void AddToCart(String idMon, String account_id, double soLuong) {
        try {
            Connection conn = DBConnection.getConnection();
 
            String checkSql = "SELECT soLuong FROM gio_hang WHERE idMon = ? AND account_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, idMon);
            checkStmt.setString(2, account_id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Đã tồn tại → cập nhật số lượng
                String updateSql = "UPDATE gio_hang SET soLuong = soLuong + ? WHERE idMon = ? AND account_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setDouble(1, soLuong);
                updateStmt.setString(2, idMon);
                updateStmt.setString(3, account_id);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                // Chưa có → thêm mới
                String insertSql = "INSERT INTO gio_hang (idMon, account_id, soLuong) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, idMon);
                insertStmt.setString(2, account_id);
                insertStmt.setDouble(3, soLuong);
                insertStmt.executeUpdate();
                insertStmt.close();
            }

            rs.close();
            checkStmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public static List<CartItem> getCartByUserId(String account_id) {
        List<CartItem> productList = new ArrayList<>();
        String query = "SELECT td.idMon, td.tenMon, td.hinhAnh, td.gia, gh.soLuong " +
                "FROM gio_hang gh JOIN thucdon td ON gh.idMon = td.idMon " +
                "WHERE gh.account_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, account_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem CartItem = new CartItem(
                        rs.getString("idMon"),
                        rs.getString("tenMon"),
                        rs.getString("hinhAnh"),
                        rs.getInt("gia"),
                        rs.getInt("soLuong"));
                productList.add(CartItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    public static void deleteCartItem(String idMon, String account_id) {
        String sql = "DELETE FROM gio_hang WHERE idMon = ? AND account_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idMon);
            ps.setString(2, account_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tăng/giảm số lượng
    public static void updateCartItem(String idMon, String account_id, int delta) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE gio_hang SET soLuong = soLuong + ? WHERE idMon = ? AND account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, delta);
            ps.setString(2, idMon);
            ps.setString(3, account_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá nếu số lượng <= 0
    public static void removeIfQuantityZero(String idMon, String account_id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM gio_hang WHERE idMon = ? AND account_id = ? AND soLuong <= 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idMon);
            ps.setString(2, account_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void clearCartByUserId(String account_id) {
        String query = "DELETE FROM gio_hang WHERE account_id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, account_id);
            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    
}
