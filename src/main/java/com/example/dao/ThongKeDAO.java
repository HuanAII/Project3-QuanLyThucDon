package com.example.dao;

import com.example.models.*;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.*;

public class ThongKeDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Thống kê doanh thu theo ngày
    public List<Map<String, Object>> getDoanhThuTheoNgay(Date startDate, Date endDate) {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT DATE(NgayLap) as Ngay, SUM(TongTien) as DoanhThu " +
                      "FROM HoaDon " +
                      "WHERE NgayLap BETWEEN ? AND ? " +
                      "GROUP BY DATE(NgayLap) " +
                      "ORDER BY Ngay";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(query);
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ngay", rs.getDate("Ngay"));
                item.put("doanhThu", rs.getDouble("DoanhThu"));
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    // Thống kê doanh thu theo tháng
    public List<Map<String, Object>> getDoanhThuTheoThang(int nam) {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT MONTH(NgayLap) as Thang, SUM(TongTien) as DoanhThu " +
                      "FROM HoaDon " +
                      "WHERE YEAR(NgayLap) = ? " +
                      "GROUP BY MONTH(NgayLap) " +
                      "ORDER BY Thang";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, nam);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("thang", rs.getInt("Thang"));
                item.put("doanhThu", rs.getDouble("DoanhThu"));
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    // Thống kê doanh thu theo năm
    public List<Map<String, Object>> getDoanhThuTheoNam() {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT YEAR(NgayLap) as Nam, SUM(TongTien) as DoanhThu " +
                      "FROM HoaDon " +
                      "GROUP BY YEAR(NgayLap) " +
                      "ORDER BY Nam";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("nam", rs.getInt("Nam"));
                item.put("doanhThu", rs.getDouble("DoanhThu"));
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    // Thống kê top món ăn bán chạy
    public List<Map<String, Object>> getTopMonAnBanChay(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT p.TenSanPham, SUM(ct.SoLuong) as SoLuongBan, " +
                      "SUM(ct.SoLuong * ct.DonGia) as DoanhThu " +
                      "FROM ChiTietDonHang ct " +
                      "JOIN Product p ON ct.MaSanPham = p.MaSanPham " +
                      "GROUP BY p.MaSanPham, p.TenSanPham " +
                      "ORDER BY DoanhThu DESC " +
                      "LIMIT ?";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("tenSanPham", rs.getString("TenSanPham"));
                item.put("soLuongBan", rs.getInt("SoLuongBan"));
                item.put("doanhThu", rs.getDouble("DoanhThu"));
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    // Thống kê top khách hàng
    public List<Map<String, Object>> getTopKhachHang(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT u.HoTen, COUNT(h.MaHoaDon) as SoDonHang, " +
                      "SUM(h.TongTien) as TongChiTieu " +
                      "FROM HoaDon h " +
                      "JOIN User u ON h.MaKhachHang = u.MaUser " +
                      "GROUP BY u.MaUser, u.HoTen " +
                      "ORDER BY TongChiTieu DESC " +
                      "LIMIT ?";
        try {
            conn = new DBConnection().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, limit);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("hoTen", rs.getString("HoTen"));
                item.put("soDonHang", rs.getInt("SoDonHang"));
                item.put("tongChiTieu", rs.getDouble("TongChiTieu"));
                result.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return result;
    }

    // Đóng tài nguyên
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 