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
        String redirectTo = "index.html"; 
        if (page != null) {
            switch (page) {
                case "trangchu":
                    redirectTo = "index.html";
                    break;
                case "about":
                    redirectTo = "about.html";
                    break;
                case "products":
                
                    redirectTo = "products.jsp";
                    break;
                case "contact":
                    redirectTo = "contact.html";
                    break;
                case "stores":
                    redirectTo = "stores.html";
                    break;
                case "booking":
                    redirectTo = "booking.html";
                    break;
                case "favorites":
                    redirectTo = "fav.html";
                    break;
                default:
                    redirectTo = "index.html";
            }
        }
        resp.sendRedirect(redirectTo);
    }
}

