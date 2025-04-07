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
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM thucdon";

        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Đọc đúng thứ tự cột từ CSDL
                Product product = new Product(
                    rs.getString("idMon"),          // Giả sử cột 1 là id
                    rs.getString("tenMon"),        // Cột 2 là tên
                    rs.getString("idDanhMuc"), 
                    rs.getDouble("gia"),      
                    rs.getString("hinhAnh"),  
                    rs.getString("moTa"), 
                    rs.getString("donViTinh")      
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ cụ thể
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý các ngoại lệ khác
        }

        return productList;
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
    
        // Xử lý lọc theo loại sản phẩm (type)
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
                    query.append(" ORDER BY gia DESC"); // Sắp xếp theo giá giảm dần
                    break;
                default:
                    // Không thay đổi gì khi chọn "mặc định"
                    break;
            }
        }
    
        // Thực hiện truy vấn và lấy kết quả từ cơ sở dữ liệu
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
                    rs.getString("donViTinh")
                );
                productList.add(product);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return productList;
    }
    
    
    
    
        public static void main(String[] args) {
        productsDAO dao = new productsDAO();
        String [] price = {}; // Ví dụ giá trị lọc
        String [] type = {}; // Ví dụ loại sản phẩm
        String sort = "price-desc"; // Ví dụ sắp xếp theo giá tăng dần
        List<Product> products = dao.getAllProductsBySort( price,sort,type);
        for (Product p : products) {
            System.out.println(p);
        }
    }
}