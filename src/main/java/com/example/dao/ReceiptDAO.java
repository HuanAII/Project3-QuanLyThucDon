package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.example.models.HoaDon;
import com.example.utils.DBConnection;

public class ReceiptDAO {
    public static boolean addHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO hoa_don (idDonHang, tenPhuonThucThanhToan, ngayThanhToan, soTien) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, hoaDon.getIdDonHang());
            ps.setString(2, hoaDon.getTenPhuongThucThanhToan());
            ps.setDate(3, hoaDon.getNgayThanhToan());
            ps.setDouble(4, hoaDon.getSoTien());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace(); // Có thể thay bằng logging
            return false;
        }
    }

public static boolean deleteHoaDon(int idHoaDon) {
    String sql = "DELETE FROM hoa_don WHERE idHoaDon = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, idHoaDon); 

        int affectedRows = ps.executeUpdate();
        return affectedRows > 0;

    } catch (Exception e) {
        e.printStackTrace(); 
        return false;
    }
}


        public static List<HoaDon> getAllHoaDon() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setIdHoaDon(rs.getInt("idHoaDon"));
                hoaDon.setIdDonHang(rs.getInt("idDonHang"));
                hoaDon.setTenPhuongThucThanhToan(rs.getString("tenPhuonThucThanhToan"));
                hoaDon.setNgayThanhToan(rs.getDate("ngayThanhToan"));
                hoaDon.setSoTien(rs.getDouble("soTien"));
                list.add(hoaDon);
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return list;
    }
    
}
