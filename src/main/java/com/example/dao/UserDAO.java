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

    // Hàm mã hóa mật khẩu với SHA-256
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

    // Tạo ID ngẫu nhiên cho user mới
    private String generateUniqueId() {
        return UUID.randomUUID().toString().substring(0, 20);
    }

    // Kiểm tra đăng nhập bằng username và password
    public User checkLoginByUsername(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // SQL truy vấn - so sánh trực tiếp với mật khẩu không mã hóa
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
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
                user.setAddress(resultSet.getString("Address"));
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
            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
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
                user.setAddress(resultSet.getString("Address"));
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
            String encryptedPassword = encryptPassword(user.getPassword());

            // SQL để thêm user mới
            String sql = "INSERT INTO user (id, username, password, role, email, Address) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, user.getUsername());
            statement.setString(3, encryptedPassword);
            statement.setString(4, user.getRole() != null ? user.getRole() : "user"); // Mặc định role là "user"
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getAddress());

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
            String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
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
            String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
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

    // Cập nhật thông tin người dùng
    public boolean updateUser(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // SQL để cập nhật thông tin user
            String sql = "UPDATE user SET username=?, role=?, email=?, Address=? WHERE id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getRole());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAddress());
            statement.setString(5, user.getId());

            // Thực thi
            int rowsUpdated = statement.executeUpdate();
            success = (rowsUpdated > 0);

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

    // Đổi mật khẩu
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean success = false;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // Mã hóa mật khẩu cũ và mới
            String encryptedOldPassword = encryptPassword(oldPassword);
            String encryptedNewPassword = encryptPassword(newPassword);

            // Kiểm tra mật khẩu cũ
            String checkSql = "SELECT * FROM user WHERE id = ? AND password = ?";
            statement = connection.prepareStatement(checkSql);
            statement.setString(1, userId);
            statement.setString(2, encryptedOldPassword);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Mật khẩu cũ đúng, cập nhật mật khẩu mới
                statement.close();

                String updateSql = "UPDATE user SET password = ? WHERE id = ?";
                statement = connection.prepareStatement(updateSql);
                statement.setString(1, encryptedNewPassword);
                statement.setString(2, userId);

                int rowsUpdated = statement.executeUpdate();
                success = (rowsUpdated > 0);
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

        return success;
    }

    // Cập nhật mật khẩu thành dạng mã hóa
    public boolean updatePasswordToHash(String username, String plainPassword) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            // Lấy kết nối database
            connection = DBConnection.getConnection();

            // Mã hóa mật khẩu
            String hashedPassword = encryptPassword(plainPassword);

            // SQL để cập nhật mật khẩu
            String sql = "UPDATE user SET password = ? WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, hashedPassword);
            statement.setString(2, username);

            // Thực thi
            int rowsUpdated = statement.executeUpdate();
            success = (rowsUpdated > 0);

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

    public boolean updateResetToken(String email, String token) throws ClassNotFoundException {
        String sql = "UPDATE users SET reset_token = ?, reset_token_expiry = ? WHERE email = ?";
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

    public boolean resetPassword(String token, String newPassword) throws ClassNotFoundException {
        String sql = "UPDATE users SET password = ?, reset_token = NULL, reset_token_expiry = NULL " +
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
}