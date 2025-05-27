<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống Kê Món Ăn</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #d35400;
            text-align: center;
            padding-bottom: 15px;
            border-bottom: 2px solid #f39c12;
            margin-bottom: 30px;
        }
        .summary-cards {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
        }
        .card {
            flex: 1;
            background: linear-gradient(135deg, #ff9966, #ff5e62);
            background: rgb(232, 108, 42);
            color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin: 0 10px;
            text-align: center;
        }
        .card:nth-child(1) {
            background: rgb(232, 108, 42);
        }
        .card:nth-child(2) {
            background: rgb(232 108 42);
        }
        .card:nth-child(3) {
            background: rgb(232 108 42);
        }
        .card h3 {
            font-size: 16px;
            margin-bottom: 10px;
        }
        .card p {
            font-size: 24px;
            font-weight: bold;
            margin: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            box-shadow: 0 2px 3px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #e67e22;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .rank-cell {
            font-weight: bold;
            text-align: center;
            width: 60px;
        }
        .rank-1 {
            background-color: #ffd700;
            color: #333;
        }
        .rank-2 {
            background-color: #c0c0c0;
            color: #333;
        }
        .rank-3 {
            background-color: #cd7f32;
            color: #fff;
        }
        .chart-container {
            margin-top: 40px;
            height: 400px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .chart-title {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .food-name {
            font-weight: bold;
            color: #333;
        }
        .badge {
            display: inline-block;
            padding: 3px 7px;
            border-radius: 50px;
            font-size: 12px;
            font-weight: bold;
            color: white;
            margin-left: 5px;
        }
        .badge-hot {
            background-color: #e74c3c;
        }
        .status-indicator {
            width: 10px;
            height: 10px;
            display: inline-block;
            border-radius: 50%;
            margin-right: 5px;
        }
        .status-high {
            background-color: #2ecc71;
        }
        .status-medium {
            background-color: #f39c12;
        }
        .status-low {
            background-color: #e74c3c;
        }
        
        /* Chart styles */
        .charts-row {
            display: flex;
            gap: 20px;
            margin-top: 30px;
        }
        .chart-container {
            flex: 1;
            height: 400px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        #quantityChart, #revenueChart {
            width: 100%;
            height: 350px;
        }
        .bar {
            fill: #3498db;
            transition: fill 0.3s;
        }
        .bar:hover {
            fill: #2980b9;
        }
        .revenue-bar {
            fill: #e67e22;
        }
        .revenue-bar:hover {
            fill: #d35400;
        }
        .chart-tooltip {
            position: absolute;
            padding: 10px;
            background: rgba(0, 0, 0, 0.8);
            color: #fff;
            border-radius: 4px;
            pointer-events: none;
            font-size: 12px;
        }
    </style>
    
    <!-- Chart.js library -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
</head>
<body>
    <div class="container">
        <h1>Thống Kê Món Ăn</h1>
        
        <div class="summary-cards">
            <c:set var="totalItems" value="0" />
            <c:set var="totalQuantity" value="0" />
            <c:set var="totalRevenue" value="0" />
            
            <c:forEach var="item" items="${thongKeMonAn}">
                <c:set var="totalItems" value="${totalItems + 1}" />
                <c:set var="totalQuantity" value="${totalQuantity + item.soLuongDat}" />
                <c:set var="totalRevenue" value="${totalRevenue + item.tongTien}" />
            </c:forEach>
            
            <div class="card">
                <h3>Tổng Số Món</h3>
                <p>${totalItems}</p>
            </div>
            <div class="card">
                <h3>Tổng Số Lượng Đặt</h3>
                <p>${totalQuantity}</p>
            </div>
            <div class="card">
                <h3>Tổng Doanh Thu</h3>
                <p><fmt:formatNumber value="${totalRevenue}" pattern="#,##0 ₫" /></p>
            </div>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Xếp Hạng</th>
                    <th>Tên Món</th>
                    <th>Số Lượng Đặt</th>
                    <th>Doanh Thu</th>
                    <th>Trạng Thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${thongKeMonAn}" varStatus="status">
                    <tr>
                        <td class="rank-cell ${status.index < 3 ? 'rank-'.concat(status.index + 1) : ''}">
                            ${status.index + 1}
                        </td>
                        <td>
                            <span class="food-name">${item.tenMon}</span>
                            <c:if test="${status.index == 0}">
                                <span class="badge badge-hot">Bán chạy nhất</span>
                            </c:if>
                        </td>
                        <td>${item.soLuongDat}</td>
                        <td><fmt:formatNumber value="${item.tongTien}" pattern="#,##0 ₫" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${item.soLuongDat > 50}">
                                    <span class="status-indicator status-high"></span>Bán chạy
                                </c:when>
                                <c:when test="${item.soLuongDat > 20}">
                                    <span class="status-indicator status-medium"></span>Trung bình
                                </c:when>
                                <c:otherwise>
                                    <span class="status-indicator status-low"></span>Bán chậm
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        

        
        <!-- Thêm biểu đồ -->
        <div class="charts-row">
            <div class="chart-container">
                <h3 class="chart-title">Thống Kê Số Lượng Đặt</h3>
                <canvas id="quantityChart"></canvas>
            </div>
            
            <div class="chart-container">
                <h3 class="chart-title">Thống Kê Doanh Thu</h3>
                <canvas id="revenueChart"></canvas>
            </div>
        </div>
        
        <!-- Script vẽ biểu đồ -->
        <script>
            document.addEventListener('DOMContentLoaded', function() {
                // Dữ liệu cho biểu đồ
                var foodNames = [];
                var quantities = [];
                var revenues = [];
                
                <c:forEach var="item" items="${thongKeMonAn}" varStatus="status">
                    <c:if test="${status.index < 10}"> // Lấy top 10 món để hiển thị
                        foodNames.push("${item.tenMon}");
                        quantities.push(${item.soLuongDat});
                        revenues.push(${item.tongTien});
                    </c:if>
                </c:forEach>
                
                // Biểu đồ số lượng
                var ctxQuantity = document.getElementById('quantityChart').getContext('2d');
                var quantityChart = new Chart(ctxQuantity, {
                    type: 'bar',
                    data: {
                        labels: foodNames,
                        datasets: [{
                            label: 'Số lượng đặt',
                            data: quantities,
                            backgroundColor: 'rgba(54, 162, 235, 0.7)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true,
                                title: {
                                    display: true,
                                    text: 'Số lượng'
                                }
                            },
                            x: {
                                ticks: {
                                    maxRotation: 45,
                                    minRotation: 45
                                }
                            }
                        },
                        plugins: {
                            title: {
                                display: false
                            },
                            legend: {
                                display: false
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(context) {
                                        return 'Số lượng: ' + context.raw;
                                    }
                                }
                            }
                        }
                    }
                });
                
                // Biểu đồ doanh thu
                var ctxRevenue = document.getElementById('revenueChart').getContext('2d');
                var revenueChart = new Chart(ctxRevenue, {
                    type: 'bar',
                    data: {
                        labels: foodNames,
                        datasets: [{
                            label: 'Doanh thu',
                            data: revenues,
                            backgroundColor: 'rgba(255, 159, 64, 0.7)',
                            borderColor: 'rgba(255, 159, 64, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true,
                                title: {
                                    display: true,
                                    text: 'Doanh thu (VNĐ)'
                                }
                            },
                            x: {
                                ticks: {
                                    maxRotation: 45,
                                    minRotation: 45
                                }
                            }
                        },
                        plugins: {
                            title: {
                                display: false
                            },
                            legend: {
                                display: false
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(context) {
                                        var value = context.raw;
                                        return 'Doanh thu: ' + value.toLocaleString('vi-VN') + ' ₫';
                                    }
                                }
                            }
                        }
                    }
                });
            });
        </script>
    </div>
</body>
</html>