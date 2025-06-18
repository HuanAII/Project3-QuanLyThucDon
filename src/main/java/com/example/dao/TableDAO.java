package com.example.dao;

import com.example.models.Table;
import com.example.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TableDAO {

    public static List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM ban_an"; 

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Table table = new Table(
                    rs.getString("id_table"),
                    rs.getInt("table_number"),
                    rs.getInt("so_cho_ngoi")
                );
                tables.add(table);
            }
            System.out.println("Tables: " + tables.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tables;
    }
public static List<Table> getBookedTables() {
    List<Table> bookedTables = new ArrayList<>();
    String sql = "SELECT * FROM ban_an WHERE id_table IN (SELECT DISTINCT id_table FROM dat_ban)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Table table = new Table(
                rs.getString("id_table"),
                rs.getInt("table_number"),
                rs.getInt("so_cho_ngoi")
            );
            bookedTables.add(table);
        }
        System.out.println("Tables booked (ban_an): " + bookedTables.size());

    } catch (Exception e) {
        e.printStackTrace();
    }

    return bookedTables;
}

public static List<Table> getAvailableTables() {
    List<Table> tables = new ArrayList<>();
    String sql = "SELECT * FROM ban_an WHERE id_table NOT IN (SELECT DISTINCT id_table FROM dat_ban)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Table table = new Table(
                rs.getString("id_table"),
                rs.getInt("table_number"),
                rs.getInt("so_cho_ngoi")
            );
            tables.add(table);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return tables;
}



// Thêm hàm lấy bàn còn trống theo ngày
public static List<Table> getAvailableTablesByDate(java.sql.Date date) {
    List<Table> availableTables = new ArrayList<>();
    String sql = "SELECT * FROM ban_an WHERE id_table NOT IN ("
               + "SELECT id_table FROM dat_ban WHERE ngay_dat = ? AND trang_thai IN ('DA_DAT', 'DANG_SU_DUNG')"
               + ")";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, date);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Table table = new Table(
                rs.getString("id_table"),
                rs.getInt("table_number"),
                rs.getInt("so_cho_ngoi")
            );
            availableTables.add(table);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return availableTables;
}

public static List<Table> getBookedTablesByDate(java.sql.Date date) {
        List<Table> bookedTables = new ArrayList<>();
    String sql = "SELECT * FROM ban_an WHERE id_table IN ("
               + "SELECT id_table FROM dat_ban WHERE ngay_dat = ? AND trang_thai IN ('DA_DAT', 'DANG_SU_DUNG')"
               + ")";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, date);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Table table = new Table(
                rs.getString("id_table"),
                rs.getInt("table_number"),
                rs.getInt("so_cho_ngoi")
            );
            bookedTables.add(table);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return bookedTables;
}


public static boolean isTableAvailableOnDate(String tableId, java.sql.Date date) {
    List<Table> availableTables = getAvailableTablesByDate(date);

    for (Table table : availableTables) {
        if (table.getIdTable().equals(tableId)) {
            return true;
        }
    }

    return false; 
}



    public static boolean addTable(Table table) {
        String sql = "INSERT INTO ban_an (id_table, table_number, so_cho_ngoi) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, table.getIdTable());
            stmt.setInt(2, table.getTableNumber());
            stmt.setInt(3, table.getSeats());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Table getTableById(String id) {
        String sql = "SELECT * FROM ban_an WHERE id_table = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Table(
                    rs.getString("id_table"),
                    rs.getInt("table_number"),
                    rs.getInt("so_cho_ngoi")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateTable(Table table) {
        String sql = "UPDATE ban_an SET table_number = ?, so_cho_ngoi = ? WHERE id_table = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, table.getTableNumber());
            stmt.setInt(2, table.getSeats());
            stmt.setString(3, table.getIdTable());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Table> getAllTablesByDate(java.sql.Date date) {
    List<Table> tables = new ArrayList<>();
    String sql = "SELECT b.id_table, b.table_number, b.so_cho_ngoi " +
                 "FROM ban_an b " +
                 "LEFT JOIN dat_ban d ON b.id_table = d.id_table AND d.ngay_dat = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setDate(1, date);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Table table = new Table(
                rs.getString("id_table"),
                rs.getInt("table_number"),
                rs.getInt("so_cho_ngoi")
            );
            tables.add(table);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return tables;
}

   
    public static boolean deleteTable(String id) {
        String sql = "DELETE FROM ban_an WHERE id_table = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
