<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<head>
    <title>Danh sách Hóa Đơn</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background-color: #f5f7fb;
            color: #333;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            padding: 25px;
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            border-bottom: 2px solid #f0f0f0;
            padding-bottom: 15px;
        }
        
        h1 {
            color: #2c3e50;
            font-size: 24px;
            font-weight: 600;
        }
        
        .btn-add {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-weight: 500;
        }
        
        .btn-add i {
            margin-right: 5px;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
        }
        
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #eaeaea;
        }
        
        th {
            background-color: #3498db;
            color: white;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 14px;
            letter-spacing: 0.5px;
        }
        
        tr:hover {
            background-color: #f7fbff;
        }
        
        tr:nth-child(even) {
            background-color: #fafafa;
        }
        
        .amount {
            font-weight: 600;
            color: #2c3e50;
        }
        
        .btn {
            padding: 8px 12px;
            margin: 2px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            color: white;
            font-size: 13px;
            transition: all 0.3s ease;
        }
        
        .btn i {
            margin-right: 5px;
        }
        
        .btn-delete {
            background-color: #e74c3c;
        }
        
        .btn-delete:hover {
            background-color: #c0392b;
        }
        
        .btn-edit {
            background-color: #3498db;
        }
        
        .btn-edit:hover {
            background-color: #2980b9;
        }
        
        .payment-method {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            background-color: #edf7ed;
            color: #1e7e34;
            font-size: 12px;
            font-weight: 500;
        }
        
        .footer {
            margin-top: 20px;
            text-align: center;
            color: #7f8c8d;
            font-size: 12px;
        }
        
        .no-records {
            text-align: center;
            padding: 30px;
            color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Danh sách Hóa Đơn</h1>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID Hóa Đơn</th>
                    <th>ID Đơn Hàng</th>
                    <th>Phương Thức Thanh Toán</th>
                    <th>Ngày Thanh Toán</th>
                    <th>Số Tiền</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${hoaDonList}" var="hoaDon">
                    <tr>
                        <td><strong>#${hoaDon.idHoaDon}</strong></td>
                        <td>#${hoaDon.idDonHang}</td>
                        <td><span class="payment-method">${hoaDon.tenPhuongThucThanhToan}</span></td>
                        <td>${hoaDon.ngayThanhToan}</td>
                        <td class="amount">
                        <fmt:formatNumber value="${hoaDon.soTien}" type="number" groupingUsed="true" />VNĐ
                        </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/list-hoadon" method="post" onsubmit="return confirm('Bạn có chắc chắn muốn xóa hóa đơn này?')">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${hoaDon.idHoaDon}">
                                    <button type="submit" class="btn btn-delete">
                                        <i class="fas fa-trash"></i> Xóa
                                    </button>
                                </form>
                            </td>

                    </tr>
                </c:forEach>
                <c:if test="${empty hoaDonList}">
                    <tr>
                        <td colspan="6" class="no-records">Không có hóa đơn nào</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <div class="footer">
            © <script>document.write(new Date().getFullYear())</script> - Hệ thống quản lý hóa đơn
        </div>
    </div>
