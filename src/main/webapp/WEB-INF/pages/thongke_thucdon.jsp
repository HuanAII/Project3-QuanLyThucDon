<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống Kê Món Ăn</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/thongke_thucdon.css">
    
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