package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.models.Product;
import com.example.utils.DBConnection;

public class productDao {

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM thucdon");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("idMon");
                String tenMon = rs.getString("tenMon");
                String danhMuc = rs.getString("idDanhMuc");
                Double gia = rs.getDouble("gia"); 
                String hinhAnh = rs.getString("hinhAnh");
                String mota = rs.getString("mota");
                String donViTinh = rs.getString("donViTinh");
                Product product = new Product(id, tenMon, danhMuc, gia, hinhAnh, mota, donViTinh);
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cú pháp database:");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public static boolean addProduct(Product product) {
        String sql = "INSERT INTO thucdon(idMon, tenMon , idDanhMuc, gia , hinhAnh, mota, donViTinh) VALUES (?, ?, ?,?, ?, ?,?)";
    
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getIdMon());
            stmt.setString(2,product.getTenMon());
            stmt.setString(3,product.getIdDanhMuc());
            stmt.setDouble(4,product.getGia());
            stmt.setString(5,product.getHinhAnh());
            stmt.setString(6,product.getMota());
            stmt.setString(7,product.getDonViTinh());
            stmt.executeUpdate(); 
            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
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
