<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="thongke-container">
    <div class="filter-section">
        <form action="thongke" method="GET" class="filter-form">
            <div class="form-group">
                <label for="startDate">Từ ngày:</label>
                <input type="date" id="startDate" name="startDate" value="${param.startDate}">
            </div>
            <div class="form-group">
                <label for="endDate">Đến ngày:</label>
                <input type="date" id="endDate" name="endDate" value="${param.endDate}">
            </div>
            <button type="submit" class="btn-filter">Lọc dữ liệu</button>
        </form>
    </div>

    <div class="summary-section">
        <div class="summary-card">
            <h3>Tổng doanh thu</h3>
            <p class="amount">${totalRevenue} VNĐ</p>
        </div>
        <div class="summary-card">
            <h3>Số hóa đơn</h3>
            <p class="count">${totalOrders}</p>
        </div>
        <div class="summary-card">
            <h3>Trung bình/Hóa đơn</h3>
            <p class="average">${averageOrder} VNĐ</p>
        </div>
    </div>

    <%-- Debug info --%>
    <div class="debug-info" style="display:none;">
        <p>Start Date: ${param.startDate}</p>
        <p>End Date: ${param.endDate}</p>
        <p>Total Records: ${revenueData.size()}</p>
    </div>

    <div class="data-section">
        <c:if test="${empty revenueData}">
            <p class="no-data">Không có dữ liệu trong khoảng thời gian này</p>
        </c:if>
        
        <c:if test="${not empty revenueData}">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Ngày</th>
                        <th>Số hóa đơn</th>
                        <th>Doanh thu</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${revenueData}" var="data">
                        <tr>
                            <td><fmt:formatDate value="${data.ngayThanhToan}" pattern="dd/MM/yyyy"/></td>
                            <td>${data.soHoaDon}</td>
                            <td><fmt:formatNumber value="${data.doanhThu}" type="currency" currencySymbol="VNĐ"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>

<style>
    .thongke-container {
        padding: 20px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .filter-section {
        margin-bottom: 20px;
        padding: 15px;
        background: #f8f9fa;
        border-radius: 6px;
    }

    .filter-form {
        display: flex;
        gap: 15px;
        align-items: center;
    }

    .form-group {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .form-group input[type="date"] {
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    .btn-filter {
        padding: 8px 16px;
        background: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .summary-section {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
    }

    .summary-card {
        padding: 20px;
        background: #f8f9fa;
        border-radius: 6px;
        text-align: center;
    }

    .summary-card h3 {
        margin-bottom: 10px;
        color: #666;
    }

    .amount, .count, .average {
        font-size: 24px;
        font-weight: bold;
        color: #28a745;
    }

    .data-table {
        width: 100%;
        border-collapse: collapse;
    }

    .data-table th, .data-table td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    .data-table th {
        background: #f8f9fa;
        font-weight: bold;
    }
</style>
