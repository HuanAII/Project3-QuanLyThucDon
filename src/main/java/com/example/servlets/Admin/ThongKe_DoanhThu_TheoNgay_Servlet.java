package com.example.servlets.Admin;

import com.example.dao.OrderDAO;
import com.example.models.DonHang;
import com.example.models.ChiTietDonHang;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/admin/thongke_doanhthu_theongay")
public class ThongKe_DoanhThu_TheoNgay_Servlet extends HttpServlet {

    public static class ThongKeMonAn {
        private String tenMon;
        private int soLuongDat;
        private double tongTien;
        private Date ngayDat;

        public ThongKeMonAn(String tenMon, Date ngayDat) {
            this.tenMon = tenMon;
            this.ngayDat = ngayDat;
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

        public Date getNgayDat() {
            return ngayDat;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;

        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = sdf.parse(startDateStr);
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = sdf.parse(endDateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            req.setAttribute("error", "Ngày nhập không hợp lệ!");
            req.getRequestDispatcher("/WEB-INF/pages/thongke_doanhthu_theongay.jsp").forward(req, resp);
            return;
        }

        List<DonHang> donHangList = OrderDAO.getAllOrders();

        if (donHangList == null) donHangList = new ArrayList<>();

        // Map: <Tên Món, Map<Ngày Đặt, Thống Kê Món>>
        Map<String, Map<Date, ThongKeMonAn>> thongKeMap = new HashMap<>();

        for (DonHang dh : donHangList) {
            Date ngayDat = dh.getDate();
            if (ngayDat == null) continue;

            // Kiểm tra ngày đặt có nằm trong khoảng startDate - endDate không
            if (startDate != null && ngayDat.before(startDate)) continue;
            if (endDate != null && ngayDat.after(endDate)) continue;

            List<ChiTietDonHang> chiTietList = dh.getChiTietList();
            if (chiTietList == null) continue;

            for (ChiTietDonHang ct : chiTietList) {
                String tenMon = ct.getTenMon();

                Map<Date, ThongKeMonAn> theoNgayMap = thongKeMap.computeIfAbsent(tenMon, k -> new HashMap<>());
                ThongKeMonAn tk = theoNgayMap.get(ngayDat);

                if (tk == null) {
                    tk = new ThongKeMonAn(tenMon, ngayDat);
                    theoNgayMap.put(ngayDat, tk);
                }

                tk.congSoLuong(ct.getSoLuong());
                tk.congTien(ct.getGia() * ct.getSoLuong());
            }
        }

        // Chuyển sang danh sách kết quả
        List<ThongKeMonAn> resultList = new ArrayList<>();
        for (Map<Date, ThongKeMonAn> map : thongKeMap.values()) {
            resultList.addAll(map.values());
        }

        // Sắp xếp theo ngày giảm dần
        resultList.sort((a, b) -> b.getNgayDat().compareTo(a.getNgayDat()));

        req.setAttribute("thongKeMonAn", resultList);
        req.setAttribute("contentPage", "/WEB-INF/pages/thongke_doanhthu_theongay.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
