package com.example.dao;

import com.example.models.ReservationItem;
import com.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class reservation_item_DAO {

    public static boolean save(ReservationItem item) throws SQLException {
        String sql = "INSERT INTO reservation_item (reservation_id, mon_an_id, ten_mon, so_luong, gia) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getReservationId());
            stmt.setString(2, item.getMonAnId());
            stmt.setString(3, item.getTenMon());
            stmt.setInt(4, item.getSoLuong());
            stmt.setInt(5, item.getGia());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        }
        catch (ClassNotFoundException e) {
            System.err.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        catch (SQLException e) {
            System.err.println("Lỗi khi lưu món ăn đặt bàn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<ReservationItem> getAllReservationItem() {
    List<ReservationItem> itemList = new ArrayList<>();
    String sql = "SELECT id, reservation_id, mon_an_id, ten_mon, so_luong, gia FROM reservation_item";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id");
            int reservationId = rs.getInt("reservation_id");
            String monAnId = rs.getString("mon_an_id");
            String tenMon = rs.getString("ten_mon");
            int soLuong = rs.getInt("so_luong");
            int gia = rs.getInt("gia");

            ReservationItem item = new ReservationItem(id, reservationId, monAnId, tenMon, soLuong, gia);
            itemList.add(item);
        }
    } catch (Exception e) {
        System.err.println("Lỗi khi lấy danh sách món ăn đặt bàn: " + e.getMessage());
        e.printStackTrace();
    }

    return itemList;
}

}
