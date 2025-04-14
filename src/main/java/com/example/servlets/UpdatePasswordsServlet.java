package com.example.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dao.UserDAO;
import com.example.utils.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/updatePasswords")
public class UpdatePasswordsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder result = new StringBuilder();

        try {
            connection = DBConnection.getConnection();

            // Lấy tất cả username và password từ database
            String sql = "SELECT username, password FROM user";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            // Cập nhật từng mật khẩu
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String plainPassword = resultSet.getString("password");

                // Cập nhật mật khẩu thành dạng mã hóa
                boolean updated = userDAO.updatePasswordToHash(username, plainPassword);

                result.append("User: ").append(username)
                        .append(" - Update: ").append(updated ? "Success" : "Failed")
                        .append("<br>");
            }

            // Trả về kết quả
            response.setContentType("text/html");
            response.getWriter().println("<h2>Password Update Results:</h2>");
            response.getWriter().println(result.toString());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}