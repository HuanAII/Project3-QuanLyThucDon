<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Đơn hàng của bạn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            margin: 0;
            padding: 0;
            color: #000; /* MÀU CHỮ ĐEN */
        }

        .container {
            padding: 20px 40px;
        }

        h2 {
            color: #000; /* MÀU CHỮ ĐEN */
        }

        .order-container {
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 30px;
            color: #000; /* MÀU CHỮ ĐEN */
        }

        .order-header {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #000; /* MÀU CHỮ ĐEN */
        }

        .order-details {
            margin-bottom: 15px;
            color: #000; /* MÀU CHỮ ĐEN */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            color: #000; /* MÀU CHỮ ĐEN */
        }

        th, td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f0f0f0;
        }

        .total {
            text-align: right;
            font-weight: bold;
            margin-top: 10px;
            color: #000; /* MÀU CHỮ ĐEN */
        }

        hr {
            border: none;
            border-top: 1px solid #ccc;
        }
    </style>
</head>
<body>

    <!-- Nhúng menu điều hướng -->
    <jsp:include page="menu.jsp" />

    <div class="container">
        <h2> Danh sách đơn hàng của bạn</h2>

        <c:forEach var="o" items="${listDH}">
            <div class="order-container">
                <div class="order-header">Đơn hàng #${o.idDonHang} - ${o.date}</div>
                <div class="order-details">
                    👤 ${o.tenKH} - 📞 ${o.sdt} <br>
                    📍 Địa chỉ: ${o.diaChi} <br>
                    🚚 Trạng thái: ${o.status}
                </div>
                <table>
                    <tr>
                        <th>Món ăn</th>
                        <th>Số lượng</th>
                        <th>Đơn giá</th>
                        <th>Thành tiền</th>
                    </tr>
                    <c:forEach var="ct" items="${o.chiTietList}">
                        <tr>
                            <td>${ct.tenMon}</td>
                            <td>${ct.soLuong}</td>
                            <td>${ct.gia}₫</td>
                            <td>${ct.soLuong * ct.gia}₫</td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="total">Tổng cộng: ${o.total}₫</div>
            </div>
        </c:forEach>
    </div>

</body>
</html>
