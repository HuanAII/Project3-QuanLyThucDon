package com.example.servlets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import com.example.dao.reservationDAO;
import com.example.models.reservation;
@WebServlet("/reservation")
public class reservationServlet extends HttpServlet { 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
            //luu du lieu vao waitingList       
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                    System.out.println(line); 
                }
            }
            try {
                JSONObject jsonData = new JSONObject(jsonBuilder.toString());
                String name = jsonData.getString("name");
                String phone = jsonData.getString("phone");
                int guests = jsonData.getInt("guests");
                String date = jsonData.getString("date");
                String time = jsonData.getString("time");
                String message = jsonData.getString("message");
                reservation waitingReservation = new reservation(name, phone , guests, date, time, message);
                boolean saveWaitingReservation = reservationDAO.saveWaitingReservation(waitingReservation);
                response.setContentType("application/json");
                 PrintWriter out = response.getWriter();
                if (saveWaitingReservation){
                    out.write("{\"status\": \"success\", \"message\": \"Đăng kí đặt bàn thành công! Chúng tôi sẽ liên hệ bạn sau \"}");
                }
                else  out.write("{\"status\": \"fail\", \"message\": \"Không thể lưu đặt bàn\"}");
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Đã có lỗi xảy ra!\"}");
                e.printStackTrace();
            }
    }
}

