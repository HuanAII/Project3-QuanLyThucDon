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
}
