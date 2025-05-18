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

@WebServlet("/admin/thongke_doanhthu_theothang")
public class ThongKe_DoanhThu_TheoThang_Servlet extends HttpServlet {

    public static class ThongKeMonAn {
        private String tenMon;
        private int soLuongDat;
        private double tongTien;
        private String thangNam;

        public ThongKeMonAn(String tenMon, String thangNam) {
            this.tenMon = tenMon;
            this.thangNam = thangNam;
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

        public String getThangNam() {
            return thangNam;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startMonthStr = req.getParameter("startMonth"); // yyyy-MM
        String endMonthStr = req.getParameter("endMonth");     // yyyy-MM

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date startMonth = null;
        Date endMonth = null;

        try {
            if (startMonthStr != null && !startMonthStr.isEmpty()) {
                startMonth = sdf.parse(startMonthStr);
            }
            if (endMonthStr != null && !endMonthStr.isEmpty()) {
                endMonth = sdf.parse(endMonthStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            req.setAttribute("error", "Tháng nhập không hợp lệ!");
            req.getRequestDispatcher("/WEB-INF/pages/thongke_doanhthu_theothang.jsp").forward(req, resp);
            return;
        }

        List<DonHang> donHangList = OrderDAO.getAllOrders();
        if (donHangList == null) donHangList = new ArrayList<>();

        // Map: <Tên Món, Map<Tháng-Năm, Thống Kê>>
        Map<String, Map<String, ThongKeMonAn>> thongKeMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        for (DonHang dh : donHangList) {
            Date ngayDat = dh.getDate();
            if (ngayDat == null) continue;

            // Lấy tháng-năm
            calendar.setTime(ngayDat);
            String thangNam = String.format("%d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
            Date thangNamDate;
            try {
                thangNamDate = sdf.parse(thangNam);
            } catch (ParseException e) {
                continue;
            }

            // So sánh nằm trong khoảng tháng
            if (startMonth != null && thangNamDate.before(startMonth)) continue;
            if (endMonth != null && thangNamDate.after(endMonth)) continue;

            List<ChiTietDonHang> chiTietList = dh.getChiTietList();
            if (chiTietList == null) continue;

            for (ChiTietDonHang ct : chiTietList) {
                String tenMon = ct.getTenMon();

                Map<String, ThongKeMonAn> theoThangMap = thongKeMap.computeIfAbsent(tenMon, k -> new HashMap<>());
                ThongKeMonAn tk = theoThangMap.get(thangNam);

                if (tk == null) {
                    tk = new ThongKeMonAn(tenMon, thangNam);
                    theoThangMap.put(thangNam, tk);
                }

                tk.congSoLuong(ct.getSoLuong());
                tk.congTien(ct.getGia() * ct.getSoLuong());
            }
        }

        // Chuyển sang danh sách kết quả
        List<ThongKeMonAn> resultList = new ArrayList<>();
        for (Map<String, ThongKeMonAn> map : thongKeMap.values()) {
            resultList.addAll(map.values());
        }

        // Sắp xếp theo tháng giảm dần
        resultList.sort((a, b) -> b.getThangNam().compareTo(a.getThangNam()));

        req.setAttribute("thongKeMonAn", resultList);
        req.setAttribute("contentPage", "/WEB-INF/pages/thongke_doanhthu_theothang.jsp");
        req.getRequestDispatcher("/WEB-INF/admistration.jsp").forward(req, resp);
    }
}
