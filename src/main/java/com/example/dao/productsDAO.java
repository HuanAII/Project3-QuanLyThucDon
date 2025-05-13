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
}