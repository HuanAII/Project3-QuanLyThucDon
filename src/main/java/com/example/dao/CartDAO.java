package com.example.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.example.models.CartItem;
import com.example.models.ChiTietDonHang;
import com.example.models.DonHang;
import com.example.models.Product;
import com.example.utils.DBConnection;

public class CartDAO {
    public static void AddToCart(String idMon, String id_kh, double soLuong) {
        try {
            Connection conn = DBConnection.getConnection();
            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
            String checkSql = "SELECT soLuong FROM gio_hang WHERE idMon = ? AND id_kh = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, idMon);
            checkStmt.setString(2, id_kh);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Đã tồn tại → cập nhật số lượng
                String updateSql = "UPDATE gio_hang SET soLuong = soLuong + ? WHERE idMon = ? AND id_kh = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setDouble(1, soLuong);
                updateStmt.setString(2, idMon);
                updateStmt.setString(3, id_kh);
                updateStmt.executeUpdate();
                updateStmt.close();
            } else {
                // Chưa có → thêm mới
                String insertSql = "INSERT INTO gio_hang (idMon, id_kh, soLuong) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, idMon);
                insertStmt.setString(2, id_kh);
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

    public static List<CartItem> getCartByUserId(String id_kh) {
        List<CartItem> productList = new ArrayList<>();
        String query = "SELECT td.idMon, td.tenMon, td.hinhAnh, td.gia, gh.soLuong " +
                "FROM gio_hang gh JOIN thucdon td ON gh.idMon = td.idMon " +
                "WHERE gh.id_kh = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id_kh);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem CartItem = new CartItem(
                        rs.getString("idMon"),
                        rs.getString("tenMon"),
                        rs.getString("hinhAnh"),
                        rs.getDouble("gia"),
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

    public static void deleteCartItem(String idMon, String id_kh) {
        String sql = "DELETE FROM gio_hang WHERE idMon = ? AND id_kh = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idMon);
            ps.setString(2, id_kh);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tăng/giảm số lượng
    public static void updateCartItem(String idMon, String id_kh, int delta) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE gio_hang SET soLuong = soLuong + ? WHERE idMon = ? AND id_kh = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, delta);
            ps.setString(2, idMon);
            ps.setString(3, id_kh);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá nếu số lượng <= 0
    public static void removeIfQuantityZero(String idMon, String id_kh) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM gio_hang WHERE idMon = ? AND id_kh = ? AND soLuong <= 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, idMon);
            ps.setString(2, id_kh);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void clearCartByUserId(String id_kh) {
        String query = "DELETE FROM gio_hang WHERE id_kh = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id_kh);
            ps.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    
}
