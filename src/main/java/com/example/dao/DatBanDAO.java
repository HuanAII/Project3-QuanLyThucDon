package com.example.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.LocalTime;

import com.example.models.reservation;
import com.example.utils.DBConnection;

public class DatBanDAO {
    public static boolean updateTableStatus(String idTable, Date ngayDat, String trangThai) {
        String sql = "UPDATE dat_ban SET trang_thai = ? WHERE id_table = ? AND ngay_dat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            stmt.setString(2, idTable);
            stmt.setDate(3, ngayDat);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean insertDatBan(String idTable, String tenKH, String sdt, Date ngayDat, String trangThai) {
    String sql = "INSERT INTO dat_ban (id_table, ten_khach, sdt_khach, ngay_dat, gio_dat, ghiChu, so_Khach, trang_thai) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, idTable);
        stmt.setString(2, tenKH);
        stmt.setString(3, sdt);
        stmt.setDate(4, ngayDat);
        LocalTime now = LocalTime.now();
        stmt.setTime(5, Time.valueOf(now));
        stmt.setString(6, null);  
        stmt.setInt(7, 0);      
        stmt.setString(8, trangThai);

        return stmt.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
    }
    public static reservation getReservationByOrderId(String orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationByOrderId'");
    }
}
