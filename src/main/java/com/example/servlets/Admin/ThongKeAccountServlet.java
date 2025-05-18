package com.example.servlets.Admin;

import com.example.dao.OrderDAO;
import com.example.models.DonHang;
import com.example.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/admin/thongke_account")
public class ThongKeAccountServlet extends HttpServlet {

    public static class ThongKe {
        private String tenKH;
        private int soDon;
        private double tongTien;

        public ThongKe(String tenKH) {
            this.tenKH = tenKH;
            this.soDon = 0;
            this.tongTien = 0;
        }

        public void congDon(double tien) {
            soDon++;
            tongTien += tien;
        }

        public String getTenKH() {
            return tenKH;
        }

        public int getSoDon() {
            return soDon;
        }

        public double getTongTien() {
            return tongTien;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DonHang> donHangList = OrderDAO.getAllOrders();

        Map<Integer, ThongKe> thongKeMap = new HashMap<>();

        for (DonHang dh : donHangList) {
            int accountId = dh.getAccountId();

            // Bỏ qua các đơn hàng có accountId <= 0 (không hợp lệ)
            if (accountId <= 0) {
                continue;
            }

            ThongKe tk = thongKeMap.get(accountId);
            if (tk == null) {
                // Lấy tên khách từ bảng user_account qua UserDAO
                String tenKH = UserDAO.getHoVaTenByID(accountId);
                tk = new ThongKe(tenKH);
                thongKeMap.put(accountId, tk);
            }
            tk.congDon(dh.getTotal());
        }

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map.Entry<Integer, ThongKe> entry : thongKeMap.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("accountId", entry.getKey());
            item.put("tenKH", entry.getValue().getTenKH());
            item.put("soDon", entry.getValue().getSoDon());
            item.put("tongTien", entry.getValue().getTongTien());
            resultList.add(item);
        }

        req.setAttribute("thongKeTheoAccount", resultList);
        req.setAttribute("contentPage", "/WEB-INF/pages/thongke_account.jsp");
         req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
}
    }
