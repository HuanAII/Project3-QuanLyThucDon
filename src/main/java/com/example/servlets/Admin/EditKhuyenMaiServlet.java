package com.example.servlets.Admin;

import com.example.dao.KhuyenMaiDAO;
import com.example.models.KhuyenMai;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/admin/sua-khuyenmai")
public class EditKhuyenMaiServlet extends HttpServlet {

 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            // Lấy dữ liệu từ form
            String proId = request.getParameter("pro_id");
            double discount = Double.parseDouble(request.getParameter("discount"));
            String maGiamGia = request.getParameter("ma_giam_gia");
            String startDateStr = request.getParameter("start_date");
            String endDateStr = request.getParameter("end_date");

            // Chuyển đổi chuỗi ngày tháng sang LocalDate
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Tạo đối tượng KhuyenMai và set giá trị
            KhuyenMai km = new KhuyenMai();
            km.setProId(proId);
            km.setDiscount(discount);
            km.setMaGiamGia(maGiamGia);
            km.setStartDate(startDate);
            km.setEndDate(endDate);

            // Cập nhật vào database (bạn cần tự viết method update trong DAO)
            boolean updated = KhuyenMaiDAO.update(km);

            if (updated) {
                // Cập nhật thành công, chuyển hướng về trang danh sách khuyến mãi
                response.sendRedirect(request.getContextPath() + "/admin/list-khuyenmai");
            } else {
                // Cập nhật thất bại, báo lỗi (bạn có thể tùy chỉnh thêm)
                request.setAttribute("error", "Cập nhật khuyến mãi thất bại!");
                request.getRequestDispatcher("/admin/list-khuyenmai").forward(request, response);
            }

        } catch (NumberFormatException | DateTimeParseException e) {
            // Xử lý lỗi nhập liệu
            request.setAttribute("error", "Dữ liệu không hợp lệ, vui lòng kiểm tra lại!");
            request.getRequestDispatcher("/admin/list-khuyenmai").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu cần có thể xử lý GET ở đây, ví dụ load dữ liệu hiện tại để hiển thị form sửa
        request.getRequestDispatcher("/admin/list-khuyenmai").forward(request, response);
    }
}