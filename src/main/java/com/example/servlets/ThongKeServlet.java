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

    public void init() {
        thongKeDAO = new ThongKeDAO();
        gson = new Gson();
    }

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
            switch (pathInfo) {
                case "/ngay":
                    String startDate = request.getParameter("startDate");
                    String endDate = request.getParameter("endDate");
                    if (startDate == null || endDate == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ngày bắt đầu hoặc ngày kết thúc");
                        return;
                    }
                    List<Map<String, Object>> doanhThuNgay = thongKeDAO.getDoanhThuTheoNgay(
                        Date.valueOf(startDate), Date.valueOf(endDate)
                    );
                    response.getWriter().write(gson.toJson(doanhThuNgay));
                    break;

                case "/thang":
                    String namStr = request.getParameter("nam");
                    if (namStr == null) {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu năm");
                        return;
                    }
                    int nam = Integer.parseInt(namStr);
                    List<Map<String, Object>> doanhThuThang = thongKeDAO.getDoanhThuTheoThang(nam);
                    response.getWriter().write(gson.toJson(doanhThuThang));
                    break;

                case "/nam":
                    List<Map<String, Object>> doanhThuNam = thongKeDAO.getDoanhThuTheoNam();
                    response.getWriter().write(gson.toJson(doanhThuNam));
                    break;

                case "/top-mon":
                    String limitMonStr = request.getParameter("limit");
                    int limitMon = limitMonStr != null ? Integer.parseInt(limitMonStr) : 10;
                    List<Map<String, Object>> topMon = thongKeDAO.getTopMonAnBanChay(limitMon);
                    response.getWriter().write(gson.toJson(topMon));
                    break;

                case "/top-khach-hang":
                    String limitKHStr = request.getParameter("limit");
                    int limitKH = limitKHStr != null ? Integer.parseInt(limitKHStr) : 10;
                    List<Map<String, Object>> topKH = thongKeDAO.getTopKhachHang(limitKH);
                    response.getWriter().write(gson.toJson(topKH));
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy endpoint");
                    break;
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 