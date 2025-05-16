<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Danh sách Hóa Đơn</title>
    <style>
        body {
            background: #181818;
            color: #f0f0f0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
        }
        h2 {
            text-align: center;
            margin: 32px 0 24px 0;
            letter-spacing: 1px;
        }
        table {
            border-collapse: separate;
            border-spacing: 0;
            width: 90%;
            margin: 0 auto 40px auto;
            background: #222;
            border-radius: 18px;
            box-shadow: 0 6px 24px rgba(0,0,0,0.5);
            overflow: hidden;
        }
        th, td {
            padding: 14px 10px;
            text-align: center;
        }
        th {
            background-color: #292929;
            color: #ff1236;
            font-size: 16px;
            font-weight: bold;
            border-bottom: 2px solid #333;
        }
        td {
            font-size: 15px;
            border-bottom: 1px solid #333;
        }
        tr:last-child td {
            border-bottom: none;
        }
        tr:hover td {
            background: #232323;
            transition: background 0.2s;
        }
        .btn-delete {
            background-color: #ff1236;
            color: white;
            border: none;
            padding: 7px 18px;
            border-radius: 20px;
            cursor: pointer;
            font-weight: bold;
            font-size: 14px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.2);
            transition: background 0.2s, transform 0.2s;
        }
        .btn-delete:hover {
            background: #d0102e;
            transform: scale(1.07);
        }
    </style>
</head>
<body>
    <h2>Danh sách Hóa Đơn</h2>
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
        <c:forEach var="hoaDon" items="${hoaDonList}">
            <tr>
                <td>${hoaDon.idHoaDon}</td>
                <td>${hoaDon.idDonHang}</td>
                <td>${hoaDon.tenPhuongThucThanhToan}</td>
                <td>${hoaDon.ngayThanhToan}</td>
                <td>${hoaDon.soTien}</td>
                <td>
                    <form method="post" action="delete-hoadon" onsubmit="return confirm('Bạn có chắc muốn xóa?');">
                        <input type="hidden" name="idHoaDon" value="${hoaDon.idHoaDon}">
                        <button class="btn-delete" type="submit">Xoá</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
