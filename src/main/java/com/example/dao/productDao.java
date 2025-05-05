package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Category;
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

    
    public static boolean deleteProduct(String idMon) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM thucdon WHERE idMon = ?"; 
    
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            conn = DBConnection.getConnection();
    
          
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, idMon); 
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
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

    public static boolean updateProduct(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
    
        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE thucdon SET tenMon = ?, idDanhMuc = ?, gia = ?, hinhAnh = ?, mota = ?, donViTinh = ? WHERE idMon = ?";
    

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getTenMon()); 
            stmt.setString(2, product.getIdDanhMuc());
            stmt.setDouble(3, product.getGia());
            stmt.setString(4, product.getHinhAnh());
            stmt.setString(5, product.getMota());
            stmt.setString(6, product.getDonViTinh());
            stmt.setString(7, product.getIdMon());
    
   
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            System.err.println("Lỗi thao tác với cơ sở dữ liệu:");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi không xác định:");
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


    public static Product getProductById(String idMon) {
        String sql = "SELECT idMon, tenMon, idDanhMuc, gia, hinhAnh, mota, donViTinh FROM thucdon WHERE idMon = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, idMon);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setIdMon(rs.getString("idMon"));
                product.setTenMon(rs.getString("tenMon"));
                product.setIdDanhMuc(rs.getString("idDanhMuc"));
                product.setGia(rs.getDouble("gia"));
                product.setHinhAnh(rs.getString("hinhAnh"));
                product.setMota(rs.getString("mota"));
                product.setDonViTinh(rs.getString("donViTinh"));
                return product;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) { 
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
