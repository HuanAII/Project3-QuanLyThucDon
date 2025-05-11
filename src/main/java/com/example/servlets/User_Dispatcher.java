package com.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/user")
public class User_Dispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        String redirectTo = "index.jsp"; 
        if (page != null) {
            switch (page) {
                case "trangchu":
                    redirectTo = "index.jsp";
                    break;
                case "about":
                    redirectTo = "about.jsp";
                    break;
                case "products":
                
                    redirectTo = "products.jsp";
                    break;
                case "contact":
                    redirectTo = "contact.jsp";
                    break;
                case "stores":
                    redirectTo = "stores.jsp";
                    break;
                case "booking":
                    redirectTo = "booking.jsp";
                    break;
                default:
                    redirectTo = "index.jsp";
            }
        }
        resp.sendRedirect(redirectTo);
    }
}

