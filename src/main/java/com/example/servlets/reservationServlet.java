package com.example.servlets;

import java.io.IOException;
import java.util.List;

import com.example.dao.reservationDAO;
import com.example.models.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/reservation")
public class reservationServlet extends HttpServlet { 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
        throws ServletException, IOException {
       req.getRequestDispatcher("booking.html").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Table> reservations = reservationDAO.getAllTables(); 
        System.out.println("Số lượng bàn: " + reservations.size()); 

        for (Table table : reservations) {
            System.out.println("ID: " + table.getIdTable() +
                               ", Số bàn: " + table.getTableNumber() +
                               ", Số ghế: " + table.getSeats() +
                               ", Trạng thái: " + table.getStatus());
        }
    }

    
}

