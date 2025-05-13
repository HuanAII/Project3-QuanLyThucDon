package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.example.models.Product;
import com.example.utils.DBConnection;

public class productsDAO { // Class để truy cập dữ liệu sản phẩm từ cơ sở dữ liệu
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
                        rs.getDouble("gia"),
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

    public static Product getProductByID(String id) {
        Product product = null;
        String query = "SELECT * FROM thucdon WHERE idMon = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                            rs.getString("idMon"),
                            rs.getString("tenMon"),
                            rs.getString("idDanhMuc"),
                            rs.getDouble("gia"),
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






    public List<Product> getAllProductsBySort(String[] price, String sort, String[] type) {
        List<Product> productList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM thucdon");
        boolean isFirstCondition = true;

        // Xử lý lọc theo nhiều giá trị price
        if (price != null && price.length > 0) {
            for (String p : price) {
                switch (p) {
                    case "under-100":
                        if (isFirstCondition) {
                            query.append(" WHERE gia < 100000");
                            isFirstCondition = false;
                        } else {
                            query.append("OR gia < 100000");
                        }
                        break;
                    case "100-300":
                        if (isFirstCondition) {
                            query.append(" WHERE gia >= 100000 AND gia <= 300000");
                            isFirstCondition = false;
                        } else {
                            query.append(" OR gia >= 100000 AND gia <= 300000");
                        }
                        break;
                    case "300-500":
                        if (isFirstCondition) {
                            query.append(" WHERE gia > 300000 AND gia <= 500000");
                            isFirstCondition = false;
                        } else {
                            query.append(" OR gia > 300000 AND gia <= 500000");
                        }
                        break;
                    case "over-500":
                        if (isFirstCondition) {
                            query.append(" WHERE gia > 500000");
                            isFirstCondition = false;
                        } else {
                            query.append(" OR gia > 500000");
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        
        if (type != null && type.length > 0) {
            for (String t : type) {
                switch (t) {
                    case "food":
                        if (isFirstCondition) {
                            query.append(" WHERE idDanhMuc = 'DM0001'");
                            isFirstCondition = false;
                        } else {
                            query.append(" AND idDanhMuc = 'DM0001'");
                        }
                        break;
                    case "drink":
                        if (isFirstCondition) {
                            query.append(" WHERE idDanhMuc = 'DM0003'");
                            isFirstCondition = false;
                        } else {
                            query.append(" AND idDanhMuc = 'DM0003'");
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        // Sắp xếp theo sort
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "name-asc":
                    query.append(" ORDER BY tenMon ASC"); // Sắp xếp theo tên sản phẩm (A-Z)
                    break;
                case "price-asc":
                    query.append(" ORDER BY gia ASC"); // Sắp xếp theo giá tăng dần
                    break;
                case "price-desc":
                    query.append(" ORDER BY gia DESC"); 
                    break;
                default:
                    break;
            }
        }

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query.toString())) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("idMon"),
                        rs.getString("tenMon"),
                        rs.getString("idDanhMuc"),
                        rs.getDouble("gia"),
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