package com.example.servlets.User;
import com.example.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/DiscountServlet")
public class DiscountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String maGiamGia = request.getParameter("maGiamGia");
        HttpSession session = request.getSession();

        int tongTien = (int) (session.getAttribute("tongTien") != null
                ?  session.getAttribute("tongTien")
                : 0.0);

        Integer discount = OrderDAO.getValidDiscount(maGiamGia);

        if (discount != null && discount > 0) {
            int giamGia = (int) (tongTien * discount / 100.0);
            int tongTienSauGiam = tongTien - giamGia;

            session.setAttribute("maGiamGia", maGiamGia);
            session.setAttribute("giamGia", giamGia);
            session.setAttribute("tongTienSauGiam", tongTienSauGiam);
            session.setAttribute("discount", discount);
        } else {
            session.setAttribute("maGiamGia", "");
            session.setAttribute("giamGia", 0.0);
            session.setAttribute("tongTienSauGiam", tongTien);
            session.setAttribute("discount", 0);
        }

        System.out.println("Da giam gia ma giam gia: " + maGiamGia);
        response.sendRedirect("thanhToan.jsp");
    }
}
