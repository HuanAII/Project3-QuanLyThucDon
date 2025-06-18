<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống Kê Theo Tài Khoản</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #343a40;
            text-align: center;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #4e73df;
            color: white;
            position: sticky;
            top: 0;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #e9ecef;
        }
        .summary {
            margin-top: 30px;
            padding: 15px;
            background-color: #e8f4ff;
            border-radius: 5px;
            font-weight: bold;
        }
        .chart-container {
            margin-top: 30px;
            height: 400px;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            color: #4e73df;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
            border: 1px solid #ddd;
            margin: 0 4px;
        }
        .pagination a.active {
            background-color: #4e73df;
            color: white;
            border: 1px solid #4e73df;
        }
        .pagination a:hover:not(.active) {
            background-color: #ddd;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            color: #4e73df;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Thống Kê Theo Tài Khoản</h1>
        
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã Tài Khoản</th>
                    <th>Tên Khách Hàng</th>
                    <th>Số Đơn Hàng</th>
                    <th>Tổng Tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="totalAccounts" value="0" />
                <c:set var="totalOrders" value="0" />
                <c:set var="totalAmount" value="0" />
                
                <c:forEach var="item" items="${thongKeTheoAccount}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${item.accountId}</td>
                        <td>${item.tenKH}</td>
                        <td>${item.soDon}</td>
                        <td><fmt:formatNumber value="${item.tongTien}" pattern="#,##0 ₫" /></td>
                    </tr>
                    <c:set var="totalAccounts" value="${totalAccounts + 1}" />
                    <c:set var="totalOrders" value="${totalOrders + item.soDon}" />
                    <c:set var="totalAmount" value="${totalAmount + item.tongTien}" />
                </c:forEach>
            </tbody>
        </table>
        
        <div class="summary">
            <p>Tổng số tài khoản: ${totalAccounts}</p>
            <p>Tổng số đơn hàng: ${totalOrders}</p>
            <p>Tổng doanh thu: <fmt:formatNumber value="${totalAmount}" pattern="#,##0 ₫" /></p>
        </div>
    </div>
</body>
</html>