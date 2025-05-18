<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống Kê Doanh Thu Theo Tháng</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        .filter-container {
            background-color: #f5f5f5;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .filter-form {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .form-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        label {
            font-weight: bold;
        }
        input[type="month"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .total-row {
            font-weight: bold;
            background-color: #e9e9e9;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
        .summary-box {
            background-color: #f8f8f8;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            margin-top: 20px;
            display: flex;
            justify-content: space-around;
        }
        .summary-item {
            text-align: center;
        }
        .summary-item .value {
            font-size: 24px;
            font-weight: bold;
            color: #4CAF50;
        }
        .summary-item .label {
            color: #666;
        }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #666;
            font-style: italic;
        }
    </style>
</head>
<body>
    <h2>Thống Kê Doanh Thu Theo Tháng</h2>
    
    <div class="filter-container">
        <form class="filter-form" action="${pageContext.request.contextPath}/admin/thongke_doanhthu_theongay" method="get">
            <div class="form-group">
                <label for="startMonth">Từ tháng:</label>
                <input type="month" id="startMonth" name="startMonth" value="${param.startMonth}">
            </div>
            <div class="form-group">
                <label for="endMonth">Đến tháng:</label>
                <input type="month" id="endMonth" name="endMonth" value="${param.endMonth}">
            </div>
            <button type="submit">Lọc dữ liệu</button>
        </form>
    </div>
    
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>
    
    <c:set var="tongSoLuongTatCa" value="0" />
    <c:set var="tongTienTatCa" value="0" />
    <c:set var="soMonDuocDat" value="0" />
    
    <c:if test="${not empty thongKeMonAn}">
        <table>
            <thead>
                <tr>
                    <th>Tháng</th>
                    <th>Tên món</th>
                    <th>Số lượng đặt</th>
                    <th>Tổng tiền (VNĐ)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${thongKeMonAn}">
                    <tr>
                        <td><fmt:formatDate value="${item.ngayDat}" pattern="MM/yyyy" /></td>
                        <td>${item.tenMon}</td>
                        <td>${item.soLuongDat}</td>
                        <td><fmt:formatNumber value="${item.tongTien}" type="currency" currencySymbol="" maxFractionDigits="0"/> VNĐ</td>
                    </tr>
                    <c:set var="tongSoLuongTatCa" value="${tongSoLuongTatCa + item.soLuongDat}" />
                    <c:set var="tongTienTatCa" value="${tongTienTatCa + item.tongTien}" />
                    <c:set var="soMonDuocDat" value="${soMonDuocDat + 1}" />
                </c:forEach>
                <tr class="total-row">
                    <td colspan="2">Tổng cộng</td>
                    <td>${tongSoLuongTatCa}</td>
                    <td><fmt:formatNumber value="${tongTienTatCa}" type="currency" currencySymbol="" maxFractionDigits="0"/> VNĐ</td>
                </tr>
            </tbody>
        </table>
        
        <div class="summary-box">
            <div class="summary-item">
                <div class="value">${soMonDuocDat}</div>
                <div class="label">Món được đặt</div>
            </div>
            <div class="summary-item">
                <div class="value">${tongSoLuongTatCa}</div>
                <div class="label">Tổng số lượng</div>
            </div>
            <div class="summary-item">
                <div class="value"><fmt:formatNumber value="${tongTienTatCa}" type="currency" currencySymbol="" maxFractionDigits="0"/> VNĐ</div>
                <div class="label">Tổng doanh thu</div>
            </div>
        </div>
    </c:if>
    
    <c:if test="${empty thongKeMonAn}">
        <div class="no-data">Không có dữ liệu thống kê trong khoảng thời gian đã chọn</div>
    </c:if>
    
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Thiết lập giá trị mặc định nếu chưa có
            var today = new Date();
            var startInput = document.getElementById('startMonth');
            var endInput = document.getElementById('endMonth');
            
            if (!startInput.value) {
                // Lấy tháng đầu năm nay
                var firstMonthOfYear = new Date(today.getFullYear(), 0, 1);
                startInput.value = formatMonth(firstMonthOfYear);
            }
            
            if (!endInput.value) {
                endInput.value = formatMonth(today);
            }
            
            // Hàm format date thành YYYY-MM cho input month
            function formatMonth(date) {
                var year = date.getFullYear();
                var month = (date.getMonth() + 1).toString().padStart(2, '0');
                return year + '-' + month;
            }
        });
    </script>
</body>
</html>