<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<head>
    <title>Danh sách Hóa Đơn</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/hoadon.css">
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
        
    </div>
