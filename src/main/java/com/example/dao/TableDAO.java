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
                    rs.getInt("so_cho_ngoi"),
                    rs.getString("trang_thai")
                );
                tables.add(table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tables;
    }


    public static boolean addTable(Table table) {
        String sql = "INSERT INTO ban_an (id_table, table_number, so_cho_ngoi, trang_thai) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, table.getIdTable());
            stmt.setInt(2, table.getTableNumber());
            stmt.setInt(3, table.getSeats());
            stmt.setString(4, table.getStatus());

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
                    rs.getInt("so_cho_ngoi"),
                    rs.getString("trang_thai")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean updateTable(Table table) {
        String sql = "UPDATE ban_an SET table_number = ?, so_cho_ngoi = ?, trang_thai = ? WHERE id_table = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, table.getTableNumber());
            stmt.setInt(2, table.getSeats());
            stmt.setString(3, table.getStatus());
            stmt.setString(4, table.getIdTable());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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
