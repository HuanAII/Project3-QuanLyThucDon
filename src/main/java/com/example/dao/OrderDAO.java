package com.example.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.models.ChiTietDonHang;
import com.example.models.DonHang;
import com.example.models.HoaDon;
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


public static boolean addOrderFromWaitingReservation(reservation reservation, List<ReservationItem> listItems, String idban) {
    String sql = "INSERT INTO donhang (date, total, status, account_id, id_table, name, sdt, address) VALUES (NOW(), ?, ?, ?, ?, ?, ?, ?)";
    String status = "CHO_THANH_TOAN";
    int account_id = reservation.getIdAccount();
    String id_table = idban;
    String name = reservation.getName();
    String sdt = reservation.getPhone();
    String address = "Tai cho";

    double total = 0;
    for (ReservationItem item : listItems) {
        total += item.getGia() * item.getSoLuong();
    }

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setDouble(1, total);
        ps.setString(2, status);
        if (account_id != -1) {
            ps.setInt(3, account_id);
        } else {
            ps.setNull(3, Types.INTEGER);
        }
        ps.setString(4, id_table);
        ps.setString(5, name);
        ps.setString(6, sdt);
        ps.setString(7, address);
        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int idDonHang = rs.getInt(1);
                for (ReservationItem item : listItems) {
                    addOrderDetails(idDonHang, item.getMonAnId(), item.getSoLuong());
                }
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}



    public static boolean addOrderDetails(int idDonHang, String idMon, int soLuong) {
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
            e.printStackTrace(); // Hoặc log lỗi
        }

        return list;
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
                    System.out.println("Error during deleteFullOrder: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


        public static boolean updateOrderStatus(String orderId , String status) {
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

}
