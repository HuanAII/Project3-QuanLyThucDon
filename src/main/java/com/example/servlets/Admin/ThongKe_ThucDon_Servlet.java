package com.example.servlets.Admin;

import com.example.dao.OrderDAO;
import com.example.models.DonHang;
import com.example.models.ChiTietDonHang;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/admin/thongke_thucdon")
public class ThongKe_ThucDon_Servlet extends HttpServlet {

    public static class ThongKeMonAn {
        private String tenMon;
        private int soLuongDat;
        private double tongTien;

        public ThongKeMonAn(String tenMon) {
            this.tenMon = tenMon;
            this.soLuongDat = 0;
            this.tongTien = 0;
        }

        public void congSoLuong(int sl) {
            this.soLuongDat += sl;
        }

        public void congTien(double tien) {
            this.tongTien += tien;
        }

        public String getTenMon() {
            return tenMon;
        }

        public int getSoLuongDat() {
            return soLuongDat;
        }

        public double getTongTien() {
            return tongTien;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DonHang> donHangList = OrderDAO.getAllOrders();

        Map<String, ThongKeMonAn> thongKeMap = new HashMap<>();

        for (DonHang dh : donHangList) {
            List<ChiTietDonHang> chiTietList = dh.getChiTietList();
            if (chiTietList == null) continue;

            for (ChiTietDonHang ct : chiTietList) {
                String tenMon = ct.getTenMon();
                ThongKeMonAn tk = thongKeMap.get(tenMon);
                if (tk == null) {
                    tk = new ThongKeMonAn(tenMon);
                    thongKeMap.put(tenMon, tk);
                }
                tk.congSoLuong(ct.getSoLuong());
                tk.congTien(ct.getGia() * ct.getSoLuong());
            }
        }

        // Chuyển sang list để dễ sort
        List<ThongKeMonAn> resultList = new ArrayList<>(thongKeMap.values());

        // Sắp xếp theo số lượng đặt giảm dần
        resultList.sort((a, b) -> Integer.compare(b.getSoLuongDat(), a.getSoLuongDat()));

        req.setAttribute("thongKeMonAn", resultList);
        req.setAttribute("contentPage", "/WEB-INF/pages/thongke_thucdon.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
