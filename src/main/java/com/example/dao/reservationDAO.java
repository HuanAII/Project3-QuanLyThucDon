package com.example.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.DBConnection;
import com.example.models.ReservationItem;
import com.example.models.reservation;

public class reservationDAO {
    public static boolean saveWaitingReservation(int account_id, String ten_khach, String sdt_khach,
                                      String ngay_dat, String gio_dat, String ghi_chu,
                                      String mon_an_kem, int so_khach) throws ClassNotFoundException, SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        conn = DBConnection.getConnection();

        String sql = "INSERT INTO waiting_table_reservation " +
                     "(account_id, ten_khach, sdt_khach, ngay_dat, gio_dat, ghi_chu, mon_an_kem, so_khach) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);

        if (account_id == -1) {
            stmt.setNull(1, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(1, account_id);
        }

        stmt.setString(2, ten_khach);
        stmt.setString(3, sdt_khach);
        stmt.setDate(4, java.sql.Date.valueOf(ngay_dat));
        stmt.setString(5, gio_dat);
        stmt.setString(6, ghi_chu);
        stmt.setString(7, mon_an_kem);
        stmt.setInt(8, so_khach);

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        System.err.println("Lỗi khi lưu đặt bàn: " + e.getMessage());
        e.printStackTrace();
        return false;
    } finally {
        closeResources(stmt, conn);
    }
}


   public static boolean saveReservation(int account_id, String ten_khach, String sdt_khach,
                                      String ngay_dat, String gio_dat, String ghi_chu,
                                      String mon_an_kem, int so_khach , String id_ban) throws ClassNotFoundException, SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        conn = DBConnection.getConnection();

        String sql = "INSERT INTO waiting_table_reservation " +
                     "(account_id, ten_khach, sdt_khach,id_table, ngay_dat, gio_dat, ghi_chu, mon_an_kem, so_khach) " +
                     "VALUES (?, ?, ?,?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);

        if (account_id == -1) {
            stmt.setNull(1, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(1, account_id);
        }

        stmt.setString(2, ten_khach);
        stmt.setString(3, sdt_khach);
        stmt.setString(4, id_ban);
        stmt.setDate(5, java.sql.Date.valueOf(ngay_dat));
        stmt.setString(6, gio_dat);
        stmt.setString(7, ghi_chu);
        stmt.setString(8, mon_an_kem);
        stmt.setInt(9, so_khach);

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch (SQLException e) {
        System.err.println("Lỗi khi lưu đặt bàn: " + e.getMessage());
        e.printStackTrace();
        return false;
    } finally {
        closeResources(stmt, conn);
    }
}


public static int saveReservationReturnId(int account_id, String ten_khach, String sdt_khach,
                                          String ngay_dat, String gio_dat, String ghi_chu,
                                          String mon_an_kem, int so_khach)
        throws ClassNotFoundException, SQLException {

    Connection conn = null;
    PreparedStatement stmt = null;
    try {
        conn = DBConnection.getConnection();

        String sql = "INSERT INTO waiting_table_reservation " +
                     "(account_id, ten_khach, sdt_khach, ngay_dat, gio_dat, ghi_chu, mon_an_kem, so_khach) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        if (account_id == -1) {
            stmt.setNull(1, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(1, account_id);
        }

        stmt.setString(2, ten_khach);
        stmt.setString(3, sdt_khach);
        stmt.setDate(4, java.sql.Date.valueOf(ngay_dat));
        stmt.setString(5, gio_dat);
        stmt.setString(6, ghi_chu);
        stmt.setString(7, mon_an_kem);
        stmt.setInt(8, so_khach);

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            try (java.sql.ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);  
                }
            }
        }

        return -1; 
    } catch (SQLException e) {
        System.err.println("Lỗi khi lưu đặt bàn: " + e.getMessage());
        e.printStackTrace();
        return -1;
    } finally {
        closeResources(stmt, conn);
    }
}



    public static List<reservation> getAllWaitingReservations() {
        List<reservation> reservations = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;

        try {
            conn = DBConnection.getConnection();
            if (conn == null) return reservations;

            String sql = "SELECT id_waiting_reservation,account_id, ten_khach, sdt_khach, ngay_dat, gio_dat, ghi_chu, mon_an_kem,so_khach FROM waiting_table_reservation";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int  accountId = rs.getInt("account_id");
                if (rs.wasNull()) {
                    accountId = -1;
                }
                int id_reservation = rs.getInt("id_waiting_reservation");
                System.out.println(">> id_reservation----: " + id_reservation);
                String name = rs.getString("ten_khach");
                String phone = rs.getString("sdt_khach");
                int guests = rs.getInt("so_khach");
                String date = rs.getString("ngay_dat");
                String time = rs.getString("gio_dat");
                String message = rs.getString("ghi_chu");
                String foods = rs.getString("mon_an_kem");

                reservation res = new reservation(id_reservation,accountId, name, phone, guests, date, time, foods, message);
                reservations.add(res);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách đặt bàn: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }
        return reservations;
    }

    private static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


 
public static boolean deleteWaitingReservationById(int reservationId) {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;
    try {
        conn = DBConnection.getConnection();
        String sql = "delete  FROM waiting_table_reservation where id_waiting_reservation = ?";
        stmt = conn.prepareStatement(sql);
        System.out.println(">> reservationId: " + reservationId);
        stmt.setInt(1, reservationId);
        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0;
    } catch(ClassNotFoundException e){
        e.printStackTrace();
        System.err.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
    }
    catch (SQLException e) {
        System.err.println("Lỗi khi xóa đặt bàn: " + e.getMessage());
        e.printStackTrace();
      
    } finally {
        closeResources(rs, stmt, conn);
    }
    return false;
}

public static reservation getWaitingReservationByIdS(int reservationId) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        if (conn == null) return null;

        String sql = "SELECT id_waiting_reservation, account_id, ten_khach, sdt_khach, " +
                     "ngay_dat, gio_dat, ghi_chu, mon_an_kem, so_khach " +
                     "FROM waiting_table_reservation WHERE id_waiting_reservation = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, reservationId);

        rs = stmt.executeQuery();

        if (rs.next()) {
            Integer accountId = rs.getInt("account_id");
            if (rs.wasNull()) {
                accountId = -1;  
            }

            int resId = rs.getInt("id_waiting_reservation");
            String customerName = rs.getString("ten_khach");
            String customerPhone = rs.getString("sdt_khach");
            int numberOfGuests = rs.getInt("so_khach");
            String reservationDate = rs.getString("ngay_dat");
            String reservationTime = rs.getString("gio_dat");
            String note = rs.getString("ghi_chu");
            String attachedFoods = rs.getString("mon_an_kem");

            return new reservation(resId, accountId, customerName, customerPhone,
                                   numberOfGuests, reservationDate, reservationTime,
                                   attachedFoods, note);
        }

    } catch (Exception e) {
        System.err.println("Lỗi khi lấy đặt bàn theo ID: " + e.getMessage());
        e.printStackTrace();
    } finally {
        closeResources(rs, stmt, conn);
    }

    return null;
}



public static List<ReservationItem> getReservationItemsById(int reservationId) {
    List<ReservationItem> reservationItems = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        if (conn == null) return reservationItems;

        String sql = "SELECT id, reservation_id, mon_an_id, ten_mon, so_luong, gia " +
                     "FROM reservation_item WHERE reservation_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, reservationId);
        rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            int resId = rs.getInt("reservation_id");
            String foodId = rs.getString("mon_an_id");
            String foodName = rs.getString("ten_mon");
            int quantity = rs.getInt("so_luong");
            int price = rs.getInt("gia");

            ReservationItem item = new ReservationItem(id, resId, foodId, foodName, quantity, price);
            reservationItems.add(item);
        }
    } catch (Exception e) {
        System.err.println("Lỗi khi lấy danh sách món ăn đặt bàn: " + e.getMessage());
        e.printStackTrace();
    } finally {
        closeResources(rs, stmt, conn);
    }

    return reservationItems;
}


public static boolean saveReservationFromWaitingReservation(reservation res, String idBan) {
     Connection conn = null;
    PreparedStatement stmt = null;
    try {
        conn = DBConnection.getConnection();

        String sql = "INSERT INTO dat_ban " +
                     "(account_id, ten_khach, sdt_khach,id_table, ngay_dat, gio_dat, ghiChu, so_khach) " +
                     "VALUES (?, ?, ?,?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql);

        int account_id = res.getIdAccount();
        String ten_khach = res.getName();
        String sdt_khach = res.getPhone();
        String id_ban = idBan;
        String ngay_dat = res.getDate();
        String gio_dat = res.getTime();
        String ghi_chu = res.getMessage();
        int so_khach = res.getGuests();
        
        if (account_id == -1) {
            stmt.setNull(1, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(1, account_id);
        }

        stmt.setString(2, ten_khach);
        stmt.setString(3, sdt_khach);
        stmt.setString(4, id_ban);
        stmt.setDate(5, java.sql.Date.valueOf(ngay_dat));
        stmt.setString(6, gio_dat);
        stmt.setString(7, ghi_chu)  ;
        stmt.setInt(8, so_khach);

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;
    } catch(ClassNotFoundException e ){
        e.printStackTrace();
        return false;
    }
    catch (SQLException e) {
        System.err.println("Lỗi khi lưu đặt bàn: " + e.getMessage());
        e.printStackTrace();
        return false;
    } finally {
        closeResources(stmt, conn);
    }
}


public static List<String> getIDBookedTablesByDate(java.sql.Date ngayDat) {
    List<String> bookedTableIds = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        String sql = "SELECT DISTINCT id_table FROM dat_ban WHERE ngay_dat = ? AND id_table IS NOT NULL";
        stmt = conn.prepareStatement(sql);
        stmt.setDate(1, ngayDat);
        rs = stmt.executeQuery();

        while (rs.next()) {
            String tableId = rs.getString("id_table");
            if (tableId != null && !tableId.trim().isEmpty()) {
                bookedTableIds.add(tableId);
            }
        }
    } catch (Exception e) {
        System.err.println("Lỗi khi lấy danh sách ID bàn đã được đặt: " + e.getMessage());
        e.printStackTrace();
    } finally {
        closeResources(rs, stmt, conn);
    }

    return bookedTableIds;
}


public static Date getDateByOrderId(String orderId) {
    Date date = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection(); 
        String sql = "SELECT date FROM donhang WHERE idDonHang = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, orderId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            date = rs.getDate("date");
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return date;
}


    public static Time getGioDatByOrderIdDateAndTable( Date date, String idTable) {
    Time gioDat = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnection.getConnection();
        String sql = "SELECT gio_dat FROM dat_ban WHERE ngay_dat = ? AND id_table = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setDate(1, date);
        pstmt.setString(2, idTable);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            gioDat = rs.getTime("gio_dat");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return gioDat;
}
}