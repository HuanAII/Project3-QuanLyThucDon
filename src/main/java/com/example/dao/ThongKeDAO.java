package com.example.dao;
import com.example.utils.DBConnection;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ThongKeDAO {
    
    public List<Map<String, Object>> getDoanhThuTheoNgay(Date startDate, Date endDate) throws ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT DATE(ngayThanhToan) as Ngay, SUM(soTien) as DoanhThu " +
                      "FROM hoa_don " +
                      "WHERE DATE(ngayThanhToan) BETWEEN ? AND ? " +
                      "GROUP BY DATE(ngayThanhToan) " +
                      "ORDER BY Ngay";
                      
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("ngay", rs.getDate("Ngay"));
                    item.put("doanhThu", rs.getDouble("DoanhThu"));
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê doanh thu theo ngày", e);
        }
        return result;
    }

    public List<Map<String, Object>> getDoanhThuTheoThang(int nam) throws ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT MONTH(ngayThanhToan) as Thang, SUM(soTien) as DoanhThu " +
                      "FROM hoa_don " +
                      "WHERE YEAR(ngayThanhToan) = ? " +
                      "GROUP BY MONTH(ngayThanhToan) " +
                      "ORDER BY Thang";
                      
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, nam);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("thang", rs.getInt("Thang"));
                    item.put("doanhThu", rs.getDouble("DoanhThu"));
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê doanh thu theo tháng", e);
        }
        return result;
    }

    public List<Map<String, Object>> getDoanhThuTheoNam() throws ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT YEAR(ngayThanhToan) as Nam, SUM(soTien) as DoanhThu " +
                      "FROM hoa_don " +
                      "GROUP BY YEAR(ngayThanhToan) " +
                      "ORDER BY Nam";
                      
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("nam", rs.getInt("Nam"));
                item.put("doanhThu", rs.getDouble("DoanhThu"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê doanh thu theo năm", e);
        }
        return result;
    }

    public List<Map<String, Object>> getThongKeTheoHinhThucThanhToan() throws ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT tenPhuonThucThanhToan, " +
                      "COUNT(*) as SoLuongHoaDon, " +
                      "SUM(soTien) as TongTien " +
                      "FROM hoa_don " +
                      "WHERE tenPhuonThucThanhToan IS NOT NULL " + 
                      "GROUP BY tenPhuonThucThanhToan " +
                      "ORDER BY TongTien DESC";
                      
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("phuongThuc", rs.getString("tenPhuonThucThanhToan"));
                item.put("soLuong", rs.getInt("SoLuongHoaDon"));
                item.put("tongTien", rs.getDouble("TongTien"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê theo phương thức thanh toán", e);
        }
        return result;
    }

    public List<Map<String, Object>> getTopMonAnBanChay(int limit) throws ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT p.tenSanPham, " +
                      "COUNT(DISTINCT hd.idHoaDon) as SoLuongHoaDon, " +  // Đếm số hóa đơn riêng biệt
                      "SUM(ct.soLuong) as SoLuongBan, " +  // Thêm tổng số lượng bán
                      "SUM(ct.soLuong * ct.donGia) as DoanhThu " +  // Tính doanh thu chính xác hơn
                      "FROM hoa_don hd " +
                      "JOIN don_hang dh ON hd.idDonHang = dh.idDonHang " +
                      "JOIN chi_tiet_don_hang ct ON dh.idDonHang = ct.idDonHang " +
                      "JOIN san_pham p ON ct.idSanPham = p.idSanPham " +
                      "WHERE hd.ngayThanhToan IS NOT NULL " +  // Chỉ tính các hóa đơn đã thanh toán
                      "GROUP BY p.idSanPham, p.tenSanPham " +
                      "ORDER BY DoanhThu DESC " +
                      "LIMIT ?";
                      
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("tenSanPham", rs.getString("tenSanPham"));
                    item.put("soLuongHoaDon", rs.getInt("SoLuongHoaDon"));
                    item.put("soLuongBan", rs.getInt("SoLuongBan"));
                    item.put("doanhThu", rs.getDouble("DoanhThu"));
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê top món ăn bán chạy", e);
        }
        return result;
    }

    public List<Map<String, Object>> getTopKhachHang(int limit) throws ClassNotFoundException {
        List<Map<String, Object>> result = new ArrayList<>();
        String query = "SELECT u.hoTen, " +
                      "COUNT(DISTINCT hd.idHoaDon) as SoDonHang, " +  // Đếm số hóa đơn riêng biệt
                      "SUM(hd.soTien) as TongChiTieu, " +
                      "MAX(hd.ngayThanhToan) as LanMuaCuoi " +  // Thêm thông tin lần mua cuối
                      "FROM hoa_don hd " +
                      "JOIN don_hang dh ON hd.idDonHang = dh.idDonHang " +
                      "JOIN user u ON dh.idUser = u.idUser " +
                      "WHERE hd.ngayThanhToan IS NOT NULL " +  // Chỉ tính các hóa đơn đã thanh toán
                      "GROUP BY u.idUser, u.hoTen " +
                      "ORDER BY TongChiTieu DESC " +
                      "LIMIT ?";
                      
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("hoTen", rs.getString("hoTen"));
                    item.put("soDonHang", rs.getInt("SoDonHang"));
                    item.put("tongChiTieu", rs.getDouble("TongChiTieu"));
                    item.put("lanMuaCuoi", rs.getTimestamp("LanMuaCuoi"));
                    result.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thống kê top khách hàng", e);
        }
        return result;
    }
} 