package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.models.CartItem;
import com.example.models.Product;
import com.example.models.user;
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

    public Product getProductByID(String id) {
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
                        rs.getString("donViTinh")
                    );
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    
        return product;
    }

    public  void AddToCart(String idMon, String id_kh, double soLuong) {
        try {
            Connection conn = DBConnection.getConnection();
    
            // 1. Kiểm tra xem đã tồn tại idMon + id_kh trong giỏ hàng chưa
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
            e.printStackTrace(); // ghi log lỗi nếu có
        }
    }

    public List<CartItem> getCartByUserId(String id_kh) {
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
                    rs.getInt("soLuong")
                );
                productList.add(CartItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productList;
    }
    public void deleteCartItem(String idMon, String id_kh) {
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
        public void updateCartItem(String idMon, String id_kh, int delta) {
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
        public void removeIfQuantityZero(String idMon, String id_kh) {
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


        public int addOrder(Integer id_kh, double total, String status, String id_table, String tenKH, String sdt, String dia_chi) {
            String sql = "INSERT INTO donhang (date, total, status, id_kh, id_table, tenKH, sdt, dia_chi) VALUES (NOW(), ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDouble(1, total);
                ps.setString(2, status);
                if (id_kh != null) {
                    ps.setInt(3, id_kh);
                } else {
                    ps.setNull(3, java.sql.Types.INTEGER);
                }
                ps.setString(4, id_table);
                ps.setString(5, tenKH);
                ps.setString(6, sdt);
                ps.setString(7, dia_chi);
                ps.executeUpdate();
        
                // Lấy idDonHang vừa được tạo
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về idDonHang
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1; // Trả về -1 nếu có lỗi
        }
        
    public boolean addOrderDetails(int idDonHang, String idMon, int soLuong) {
        String sql = "INSERT INTO chitietdonhang (idDonHang, idMon, soLuong) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDonHang);
            ps.setString(2, idMon);
            ps.setInt(3, soLuong);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        // List<Product> products = dao.getAllProducts();
        // List<CartItem> products = dao.getCartByUserId("1");
        // Product product = dao.getCartByUserId("1");
        // System.out.println(products);
        dao.addOrder( 1, 40000, "Cho xu ly", null,"KhangHy", "0123456789", "Ha Noi" );
        // for (Product p : products) {
        //     System.out.println(p);
        // }
    }
}