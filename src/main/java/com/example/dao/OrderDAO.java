package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.models.ChiTietDonHang;
import com.example.models.DonHang;
import com.example.models.ReservationItem;
import com.example.models.reservation;
import com.example.utils.DBConnection;

public class OrderDAO {


        public static int addOrder(Integer account_id, double total, String status, String id_table, String name, String sdt,
            String address) {
        String sql = "INSERT INTO donhang (date, total, status, account_id, id_table, name, sdt, address) VALUES (NOW(), ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, total);
            ps.setString(2, status);
            if (account_id != null) {
                ps.setInt(3, account_id);
            } else {
                ps.setNull(3, -1);
            }
            ps.setString(4, id_table);
            ps.setString(5, name);
            ps.setString(6, sdt);
            ps.setString(7, address);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; 
        
    }


public static boolean addOrderFromWaitingReservation(reservation reservation, List<ReservationItem> listItems, String idban , java.sql.Date date) {
    String sql = "INSERT INTO donhang (date, total, status, account_id, id_table, name, sdt, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String status = "CHO_PHUC_VU";
    int account_id = reservation.getIdAccount();
    String id_table = idban;
    String name = reservation.getName();
    String sdt = reservation.getPhone();
    String address = "Tai cho";

    double total = 0;
    for (ReservationItem item : listItems) {
        total += item.getGia() * item.getSoLuong();
    }

    try (Connection conn = DBConnection.getConnection()){
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setDate(1, date);                 
            ps.setDouble(2, total);
            ps.setString(3, status);

            if (account_id != -1) {
                ps.setInt(4, account_id);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, id_table);
            ps.setString(6, name);
            ps.setString(7, sdt);
            ps.setString(8, address);

        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int idDonHang = rs.getInt(1);
                for (ReservationItem item : listItems) {
                    DetailOrderDAO.addOrderDetails(idDonHang, item.getMonAnId(), item.getSoLuong());
                }
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


        public static Integer getValidDiscount(String maGiamGia) {
            Integer discount = null;
            String query = "SELECT discount, start_date, end_date FROM khuyen_mai WHERE ma_giam_gia = ?";

            try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, maGiamGia);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // Chắc chắn đây là java.sql.Date
                    Date startDate = rs.getDate("start_date");
                    Date endDate = rs.getDate("end_date");
                    int dis = rs.getInt("discount");

                    LocalDate today = LocalDate.now();

                    // Chuyển Date thành LocalDate
                    LocalDate start = startDate.toLocalDate();
                    LocalDate end = endDate.toLocalDate();

                    if ((start.isEqual(today) || start.isBefore(today)) &&
                        (end.isEqual(today) || end.isAfter(today))) {
                        discount = dis;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return discount;
        }

public static Date getDateOfOrderById(int idDonHang) {
    String sql = "SELECT date FROM donhang WHERE idDonHang = ?";
    Date ngayDat = null;

    try (Connection conn = DBConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Không thể kết nối tới CSDL");
            return null;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDonHang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ngayDat = rs.getDate("date");
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Lỗi khi truy vấn ngày đặt đơn hàng: " + e.getMessage());
    } catch (ClassNotFoundException e1) {
        System.err.println("Lỗi không tìm thấy lớp kết nối CSDL: " + e1.getMessage());
        e1.printStackTrace();
    }

    return ngayDat;
}

 
public static List<DonHang> getAllOrders() {
    List<DonHang> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM donhang";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            DonHang dh = new DonHang();
            int idDonHang = rs.getInt("idDonHang");

            dh.setIdDonHang(idDonHang);
            dh.setDate(rs.getDate("date"));
            dh.setTotal(rs.getDouble("total"));
            dh.setStatus(rs.getString("status"));
            dh.setIdTable(rs.getString("id_table"));
            dh.setAccountId(rs.getInt("account_id"));
            dh.setTenKH(rs.getString("name"));
            dh.setSdt(rs.getString("sdt"));
            dh.setDiaChi(rs.getString("address"));

            String sqlChiTiet = "SELECT c.soLuong, m.tenMon, m.gia " +
                                "FROM chitietdonhang c JOIN thucdon m ON c.idMon = m.idMon " +
                                "WHERE c.idDonHang = ?";
            PreparedStatement psCT = conn.prepareStatement(sqlChiTiet);
            psCT.setInt(1, idDonHang);
            ResultSet rsCT = psCT.executeQuery();

            List<ChiTietDonHang> chiTietList = new ArrayList<>();
            while (rsCT.next()) {
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setTenMon(rsCT.getString("tenMon"));
                ct.setSoLuong(rsCT.getInt("soLuong"));
                ct.setGia(rsCT.getDouble("gia"));
                chiTietList.add(ct);
            }

            dh.setChiTietList(chiTietList);
            list.add(dh);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


        public static List<DonHang> getDonHangByUsername(String username) {
            List<DonHang> list = new ArrayList<>();

            try (Connection conn = DBConnection.getConnection()) {
                // Tìm ID người dùng
                String sqlGetUserId = "SELECT id FROM user_account WHERE username = ?";
                PreparedStatement psUser = conn.prepareStatement(sqlGetUserId);
                psUser.setString(1, username);
                ResultSet rsUser = psUser.executeQuery();

                if (!rsUser.next()) return list;
                int account_id = rsUser.getInt("id");

                // Lấy danh sách đơn hàng của người dùng
                String sqlDonHang = "SELECT * FROM donhang WHERE account_id = ?";
                PreparedStatement psDH = conn.prepareStatement(sqlDonHang);
                psDH.setInt(1, account_id);
                ResultSet rsDH = psDH.executeQuery();

                while (rsDH.next()) {
                    DonHang dh = new DonHang();
                    int idDonHang = rsDH.getInt("idDonHang");

                    dh.setIdDonHang(idDonHang);
                    dh.setDate(rsDH.getDate("date"));
                    dh.setTotal(rsDH.getDouble("total"));
                    dh.setStatus(rsDH.getString("status"));
                    dh.setTenKH(rsDH.getString("name"));
                    dh.setSdt(rsDH.getString("sdt"));
                    dh.setDiaChi(rsDH.getString("address"));

                    // Chi tiết đơn hàng
                    String sqlChiTiet = "SELECT c.soLuong, m.tenMon, m.gia " +
                                        "FROM chitietdonhang c JOIN thucdon m ON c.idMon = m.idMon " +
                                        "WHERE c.idDonHang = ?";
                    PreparedStatement psCT = conn.prepareStatement(sqlChiTiet);
                    psCT.setInt(1, idDonHang);
                    ResultSet rsCT = psCT.executeQuery();

                    List<ChiTietDonHang> chiTietList = new ArrayList<>();
                    while (rsCT.next()) {
                        ChiTietDonHang ct = new ChiTietDonHang();
                        ct.setTenMon(rsCT.getString("tenMon"));
                        ct.setSoLuong(rsCT.getInt("soLuong"));
                        ct.setGia(rsCT.getDouble("gia"));
                        chiTietList.add(ct);
                    }

                    dh.setChiTietList(chiTietList);
                    list.add(dh);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;
        }

        public static boolean deleteOrder(String orderId) {
            String sql = "DELETE FROM donhang WHERE idDonHang = ?";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, orderId);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        public static boolean deleteOrderDetails(String orderId) {
            String sql = "DELETE FROM chitietdonhang WHERE idDonHang = ?";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, orderId);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public static boolean deleteHoaDon(String orderId) {
            String sql = "DELETE FROM hoa_don WHERE idDonHang = ?";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, orderId);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        public static boolean deleteFullOrder(String orderId) {
            System.out.println( "Deleting order with ID: " + orderId);
    String sqlDeleteDetails = "DELETE FROM chitietdonhang WHERE idDonHang = ?";
    String sqlDeleteHoaDon = "DELETE FROM hoa_don WHERE idDonHang = ?";
    String sqlDeleteOrder = "DELETE FROM donhang WHERE idDonHang = ?";
    try (Connection conn = DBConnection.getConnection()) {
        conn.setAutoCommit(false); // Bắt đầu transaction
        
        try (PreparedStatement psDetails = conn.prepareStatement(sqlDeleteDetails);
             PreparedStatement psHoaDon = conn.prepareStatement(sqlDeleteHoaDon);
             PreparedStatement psOrder = conn.prepareStatement(sqlDeleteOrder)) {

            psDetails.setString(1, orderId);
            psDetails.executeUpdate();

            psHoaDon.setString(1, orderId);
            psHoaDon.executeUpdate();

            psOrder.setString(1, orderId);
            psOrder.executeUpdate();

            conn.commit(); // commit nếu cả 3 lệnh thành công
            return true;
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


        public static boolean updateOrderStatus(String orderId, String status) {
            String sql = "UPDATE donhang SET status = ? WHERE idDonHang = ?";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, status);
                ps.setString(2, orderId);
                ps.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

public static List<DonHang> getOdersByStatus(String status) {
    List<DonHang> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM donhang where status = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            DonHang dh = new DonHang();
            int idDonHang = rs.getInt("idDonHang");

            dh.setIdDonHang(idDonHang);
            dh.setDate(rs.getDate("date"));
            dh.setTotal(rs.getDouble("total"));
            dh.setStatus(rs.getString("status"));
            dh.setIdTable(rs.getString("id_table"));
            dh.setAccountId(rs.getInt("account_id"));
            dh.setTenKH(rs.getString("name"));
            dh.setSdt(rs.getString("sdt"));
            dh.setDiaChi(rs.getString("address"));

            String sqlChiTiet = "SELECT c.soLuong, m.tenMon, m.gia " +
                                "FROM chitietdonhang c JOIN thucdon m ON c.idMon = m.idMon " +
                                "WHERE c.idDonHang = ?";
            PreparedStatement psCT = conn.prepareStatement(sqlChiTiet);
            psCT.setInt(1, idDonHang);
            ResultSet rsCT = psCT.executeQuery();

            List<ChiTietDonHang> chiTietList = new ArrayList<>();
            while (rsCT.next()) {
                ChiTietDonHang ct = new ChiTietDonHang();
                ct.setTenMon(rsCT.getString("tenMon"));
                ct.setSoLuong(rsCT.getInt("soLuong"));
                ct.setGia(rsCT.getDouble("gia"));
                chiTietList.add(ct);
            }

            dh.setChiTietList(chiTietList);
            list.add(dh);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public static Double getTotalByOrderId(int orderId) {
    String sql = "SELECT total FROM donhang WHERE idDonHang = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble("total");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null; 

}

public static boolean updateTotal(int  orderId, double newTotal) {
    String sql = "UPDATE donhang SET total = ? WHERE idDonHang = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDouble(1, newTotal);
        ps.setInt(2, orderId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; 
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false; 
}

public static DonHang getOrderById(int orderId) {
    String sql = "SELECT * FROM donhang WHERE idDonHang = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            DonHang dh = new DonHang();
            dh.setIdDonHang(rs.getInt("idDonHang"));
            dh.setDate(rs.getDate("date"));
            dh.setTotal(rs.getDouble("total"));
            dh.setStatus(rs.getString("status"));
            dh.setIdTable(rs.getString("id_table"));
            dh.setAccountId(rs.getInt("account_id"));
            dh.setTenKH(rs.getString("name"));
            dh.setSdt(rs.getString("sdt"));
            dh.setDiaChi(rs.getString("address"));
            return dh;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public static String getIdTableByOrderId(String orderId) {
    String sql = "SELECT id_table FROM donhang WHERE idDonHang = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, orderId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("id_table");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
