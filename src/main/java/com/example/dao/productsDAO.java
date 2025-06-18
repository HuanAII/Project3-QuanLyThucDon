package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.example.models.Product;
import com.example.utils.DBConnection;

public class productsDAO { 
    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM thucdon";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
              
                Product product = new Product(
                        rs.getString("idMon"), 
                        rs.getString("tenMon"),
                        rs.getString("idDanhMuc"),
                        rs.getInt("gia"),
                        rs.getString("hinhAnh"),
                        rs.getString("moTa"),
                        rs.getString("donViTinh"));
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }

    
    public static Product getProductByID(String monId) {
        Product product = null;
        String query = "SELECT * FROM thucdon WHERE idMon = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, monId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getString("idMon"),
                            rs.getString("tenMon"),
                            rs.getString("idDanhMuc"),
                            rs.getInt("gia"),
                            rs.getString("hinhAnh"),
                            rs.getString("moTa"),
                            rs.getString("donViTinh"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return product;
    }


        public static List<Product> getProductsByCategory(String categoryId) {
            List<Product> productList = new ArrayList<>();
            String query = "SELECT * FROM thucdon WHERE idDanhMuc = ?";

            try (Connection conn = DBConnection.getConnection()) {
                if (conn != null) {
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setString(1, categoryId);
                        try (ResultSet rs = ps.executeQuery()) {
                            while (rs.next()) {
                                Product product = new Product(
                                        rs.getString("idMon"),
                                        rs.getString("tenMon"),
                                        rs.getString("idDanhMuc"),
                                        rs.getInt("gia"),
                                        rs.getString("hinhAnh"),
                                        rs.getString("moTa"),
                                        rs.getString("donViTinh")
                                );
                                productList.add(product);
                            }
                        }
                    }
                } else {
                    System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                System.out.println("Lỗi thao tác với CSDL:");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Lỗi không xác định:");
                e.printStackTrace();
            }

            return productList;
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
                product.setGia(rs.getInt("gia"));
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