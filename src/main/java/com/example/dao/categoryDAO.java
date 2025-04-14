package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Category;
import com.example.utils.DBConnection;

public class categoryDAO {
    public static List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM danh_muc";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
               
            while (rs.next()) {
                String id = rs.getString("id_danhmuc");
                String name = rs.getString("name");
                categories.add(new Category(id, name));
        
        }
    } catch (ClassNotFoundException e) {
            System.err.println("Lỗi không tìm thấy JDBC Driver:");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi thao tác với cơ sở dữ liệu:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Lỗi không xác định:");
            e.printStackTrace();
        }
        return categories;
    }

    public static boolean addCategory(Category category) {
        String sql = "INSERT INTO danh_muc(id_danhmuc, name) VALUES (?, ?)";
    
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category.getId_danhmuc());
            stmt.setString(2, category.getName_danhmuc());

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

    public static boolean removeCategory(String id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "DELETE FROM danh_muc WHERE id_danhmuc = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }catch(SQLException e){
            System.out.println("loi SQL");
            e.printStackTrace();
            return false;
        }
         catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
