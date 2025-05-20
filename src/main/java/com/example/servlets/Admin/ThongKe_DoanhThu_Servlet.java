package com.example.servlets.Admin;

import com.example.dao.OrderDAO;
import com.example.models.DonHang;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/admin/thongke_doanhthu")
public class ThongKe_DoanhThu_Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contentPage", "/WEB-INF/pages/thongke_doanhthu.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loai = req.getParameter("loai"); // "ngay", "thang", "nam"
        if (loai == null) loai = "ngay";

        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");

        SimpleDateFormat sdfNgay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfThang = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdfNam = new SimpleDateFormat("yyyy");

        Date startDate = null, endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                switch (loai) {
                    case "thang":
                        startDate = Date.valueOf(startDateStr + "-01");
                        break;
                    case "nam":
                        startDate = Date.valueOf(startDateStr + "-01-01");
                        break;
                    default:
                        startDate = Date.valueOf(startDateStr);
                }
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                switch (loai) {
                    case "thang":
                        endDate = Date.valueOf(endDateStr + "-28"); // nên dùng LocalDate để tính đúng ngày cuối tháng
                        break;
                    case "nam":
                        endDate = Date.valueOf(endDateStr + "-12-31");
                        break;
                    default:
                        endDate = Date.valueOf(endDateStr);
                }
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Ngày không hợp lệ!");
            req.setAttribute("contentPage", "/WEB-INF/pages/thongke_doanhthu.jsp");
            req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
            return;
        }

        List<DonHang> donHangList = OrderDAO.getAllOrders();

        Map<String, Double> thongKeMap = new TreeMap<>();
        double tongDoanhThu = 0;

        for (DonHang dh : donHangList) {
            Date ngayDH = new Date(dh.getDate().getTime());
            if (startDate != null && ngayDH.before(startDate)) continue;
            if (endDate != null && ngayDH.after(endDate)) continue;

            String key;
            switch (loai) {
                case "thang":
                    key = sdfThang.format(ngayDH);
                    break;
                case "nam":
                    key = sdfNam.format(ngayDH);
                    break;
                default:
                    key = sdfNgay.format(ngayDH);
            }

            double currentTotal = thongKeMap.getOrDefault(key, 0.0);
            thongKeMap.put(key, currentTotal + dh.getTotal());
            tongDoanhThu += dh.getTotal();
        }

        req.setAttribute("thongKeMap", thongKeMap);
        req.setAttribute("tongDoanhThu", tongDoanhThu);
        req.setAttribute("loai", loai);
        req.setAttribute("startDate", startDateStr);
        req.setAttribute("endDate", endDateStr);
        req.setAttribute("contentPage", "/WEB-INF/pages/thongke_doanhthu.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
