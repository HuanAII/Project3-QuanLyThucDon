package com.example.servlets;

import com.example.dao.ThongKeDAO;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/thong-ke/*")
public class ThongKeServlet extends HttpServlet {
    private ThongKeDAO thongKeDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        thongKeDAO = new ThongKeDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            request.getRequestDispatcher("/WEB-INF/thong-ke.jsp").forward(request, response);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Map<String, Object>> result = null;
            
            switch (pathInfo) {
                case "/ngay":
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");
                    if (startDate == null || endDate == null) {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ngày bắt đầu hoặc ngày kết thúc");
                        return;
                    }
                    try {
                        result = thongKeDAO.getDoanhThuTheoNgay(
                            Date.valueOf(startDate), Date.valueOf(endDate)
                        );
                    } catch (IllegalArgumentException e) {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Định dạng ngày không hợp lệ");
                        return;
                    }
                    break;

                case "/thang":
                    String namStr = request.getParameter("nam");
                    if (namStr == null) {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Thiếu năm");
                        return;
                    }
                    try {
                        int nam = Integer.parseInt(namStr);
                        result = thongKeDAO.getDoanhThuTheoThang(nam);
                    } catch (NumberFormatException e) {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Năm không hợp lệ");
                        return;
                    }
                    break;

                case "/nam":
                    result = thongKeDAO.getDoanhThuTheoNam();
                    break;

                case "/phuong-thuc-thanh-toan":
                    result = thongKeDAO.getThongKeTheoHinhThucThanhToan();
                    break;

                case "/top-mon":
                    String limitMonStr = request.getParameter("limit");
                    int limitMon = 10;
                    try {
                        if (limitMonStr != null) {
                            limitMon = Integer.parseInt(limitMonStr);
                        }
                        result = thongKeDAO.getTopMonAnBanChay(limitMon);
                    } catch (NumberFormatException e) {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Số lượng không hợp lệ");
                        return;
                    }
                    break;

                case "/top-khach-hang":
                    String limitKHStr = request.getParameter("limit");
                    int limitKH = 10;
                    try {
                        if (limitKHStr != null) {
                            limitKH = Integer.parseInt(limitKHStr);
                        }
                        result = thongKeDAO.getTopKhachHang(limitKH);
                    } catch (NumberFormatException e) {
                        sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Số lượng không hợp lệ");
                        return;
                    }
                    break;

                default:
                    sendError(response, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy endpoint");
                    return;
            }

            if (result != null) {
                String jsonResponse = gson.toJson(result);
                response.getWriter().write(jsonResponse);
            } else {
                sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể lấy dữ liệu");
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server: " + e.getMessage());
        }
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        Map<String, String> errorResponse = Map.of("error", message);
        response.getWriter().write(gson.toJson(errorResponse));
    }
} 