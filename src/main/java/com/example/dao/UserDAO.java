package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.util.UUID;
import java.sql.Timestamp;

import com.example.models.User;
import com.example.utils.DBConnection;

public class UserDAO {
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString().substring(0, 20);
    }

  
    public User checkLoginByUsername(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
      
            connection = DBConnection.getConnection();

    
            String sql = "SELECT * FROM user_account WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password); 


            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                user.setSdt(resultSet.getString("sdt"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các statement
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }

        return user;
    }

    // Kiểm tra đăng nhập bằng email và password
    public User checkLoginByEmail(String email, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // SQL truy vấn - so sánh trực tiếp với mật khẩu không mã hóa
            String sql = "SELECT * FROM user_account WHERE email = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password); // Sử dụng mật khẩu gốc không mã hóa

            // Thực thi truy vấn
            resultSet = statement.executeQuery();

            // Nếu tìm thấy user
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                user.setSdt(resultSet.getString("sdt"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các statement
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }

        return user;
    }

    // Đăng ký tài khoản mới
    public boolean registerUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // Kiểm tra username và email đã tồn tại chưa
            if (isUsernameExists(connection, user.getUsername()) || isEmailExists(connection, user.getEmail())) {
                return false;
            }

            // Tạo ID ngẫu nhiên
            String id = generateUniqueId();

            // Mã hóa mật khẩu
            

            // SQL để thêm user mới
            String sql = "INSERT INTO user_account ( username, password, role, email,sdt,HoVaTen) VALUES ( ?, ?, ?, ?, ?,?)";
            statement = connection.prepareStatement(sql);
            // statement.setString(1, id);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword()); 
            statement.setString(3, user.getRole() != null ? user.getRole() : "Khách hàng"); // Mặc định role là "Khách hàng"
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getSdt());
            statement.setString(6, user.getHoVaTen());

            // Thực thi
            int rowsInserted = statement.executeUpdate();
            success = (rowsInserted > 0);

            // Nếu thành công, cập nhật ID cho user
            if (success) {
                user.setId(id);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các statement
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }

        return success;
    }

    // Kiểm tra username đã tồn tại chưa
    private boolean isUsernameExists(Connection connection, String username) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT COUNT(*) FROM user_account WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
        }

        return false;
    }

    // Kiểm tra email đã tồn tại chưa
    private boolean isEmailExists(Connection connection, String email) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT COUNT(*) FROM user_account WHERE email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
        }

        return false;
    }

    // Cập nhật thông tin người dùng (username, email, sdt, HoVaTen)
    public boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE user_account SET username=?, email=?, sdt=?, HoVaTen=? WHERE id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getSdt());
            statement.setString(4, user.getHoVaTen());
            statement.setString(5, user.getId());
            int rowsUpdated = statement.executeUpdate();
            success = (rowsUpdated > 0);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }

        return success;
    }

    // Đổi mật khẩu (plain text)
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean success = false;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // Không mã hóa mật khẩu, so sánh trực tiếp plain text
            String checkSql = "SELECT * FROM user_account WHERE id = ? AND password = ?";
            statement = connection.prepareStatement(checkSql);
            statement.setString(1, userId);
            statement.setString(2, oldPassword);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                statement.close();

                String updateSql = "UPDATE user_account SET password = ? WHERE id = ?";
                statement = connection.prepareStatement(updateSql);
                statement.setString(1, newPassword);
                statement.setString(2, userId);

                int rowsUpdated = statement.executeUpdate();
                success = (rowsUpdated > 0);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }

        return success;
    }

    // Cập nhật mật khẩu plain text cho username (không mã hóa)
    public boolean updatePasswordToHash(String username, String plainPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE user_account SET password = ? WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, plainPassword);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();
            success = (rowsUpdated > 0);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }
        return success;
    }

    public boolean updateResetToken(String email, String token) throws ClassNotFoundException {
        String sql = "UPDATE user_account SET reset_token = ?, reset_token_expiry = ? WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp expiry = new Timestamp(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 24 hours

            stmt.setString(1, token);
            stmt.setTimestamp(2, expiry);
            stmt.setString(3, email);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đặt lại mật khẩu bằng token (plain text)
    public boolean resetPassword(String token, String newPassword) throws ClassNotFoundException {
        String sql = "UPDATE user_account SET password = ?, reset_token = NULL, reset_token_expiry = NULL " +
                "WHERE reset_token = ? AND reset_token_expiry > ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, token);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsernameOrEmail(String userValue) {
    User user = null;
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM user_account WHERE username = ? OR email = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userValue);
        ps.setString(2, userValue);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setSdt(rs.getString("sdt"));
            user.setHoVaTen(rs.getString("HoVaTen"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return user;
}

    // Lấy danh sách tất cả khách hàng
    public java.util.List<User> getAllCustomers() {
        java.util.List<User> customers = new java.util.ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM user_account WHERE role = 'Khách hàng'";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                user.setSdt(resultSet.getString("sdt"));
                user.setHoVaTen(resultSet.getString("HoVaTen"));
                customers.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) { e.printStackTrace(); }
            DBConnection.closeConnection(connection);
        }
        return customers;
    }

    // Thêm khách hàng mới (admin thêm)
    public boolean addCustomer(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        try {
            connection = DBConnection.getConnection();
            String sql = "INSERT INTO user_account (username, password, role, email, sdt, HoVaTen) VALUES (?, ?, 'Khách hàng', ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getSdt());
            statement.setString(5, user.getHoVaTen());
            int rowsInserted = statement.executeUpdate();
            success = (rowsInserted > 0);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            DBConnection.closeConnection(connection);
        }
        return success;
    }

    public static String getHoVaTenByID(int id) {
        String hoVaTen = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT HoVaTen FROM user_account WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hoVaTen = resultSet.getString("HoVaTen");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hoVaTen;
    }

    // Sửa thông tin khách hàng (admin sửa)
    public boolean updateCustomer(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        try {
            connection = DBConnection.getConnection();
            String sql = "UPDATE user_account SET username=?, password=?, email=?, sdt=?, HoVaTen=? WHERE id=? AND role='Khách hàng'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getSdt());
            statement.setString(5, user.getHoVaTen());
            statement.setString(6, user.getId());
            int rowsUpdated = statement.executeUpdate();
            success = (rowsUpdated > 0);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            DBConnection.closeConnection(connection);
        }
        return success;
    }

    // Xóa khách hàng (admin xóa)
    public boolean deleteCustomer(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        try {
            connection = DBConnection.getConnection();
            String sql = "DELETE FROM user_account WHERE id=? AND role='Khách hàng'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            int rowsDeleted = statement.executeUpdate();
            success = (rowsDeleted > 0);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            DBConnection.closeConnection(connection);
        }
        return success;
    }

    // Lấy user theo id
    public User getUserById(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DBConnection.getConnection();
            String sql = "SELECT * FROM user_account WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getString("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));
                user.setEmail(resultSet.getString("email"));
                user.setSdt(resultSet.getString("sdt"));
                user.setHoVaTen(resultSet.getString("HoVaTen"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) { e.printStackTrace(); }
            DBConnection.closeConnection(connection);
        }
        return user;
    }

}