<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống Kê Doanh Thu</title>
    <!-- Thêm base URL -->
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <base href="${contextPath}/">
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .chart-container {
            position: relative;
            margin: auto;
            width: 100%;
            height: 400px;
            margin-bottom: 30px;
        }
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #dee2e6;
            padding: 15px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-4">
        <h1 class="text-center mb-4">Thống Kê Doanh Thu</h1>

        <div class="row">
            <!-- Thống kê theo ngày -->
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Thống Kê Theo Ngày</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label class="form-label">Từ ngày:</label>
                            <input type="date" id="startDate" class="form-control">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Đến ngày:</label>
                            <input type="date" id="endDate" class="form-control">
                        </div>
                        <button onclick="loadDoanhThuNgay()" class="btn btn-primary w-100">Xem thống kê</button>
                    </div>
                </div>
            </div>

            <!-- Thống kê theo tháng -->
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Thống Kê Theo Tháng</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label class="form-label">Chọn năm:</label>
                            <select id="namThang" class="form-control">
                                <option value="2024">2024</option>
                                <option value="2023">2023</option>
                            </select>
                        </div>
                        <button onclick="loadDoanhThuThang()" class="btn btn-primary w-100">Xem thống kê</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Biểu đồ doanh thu -->
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">Biểu Đồ Doanh Thu</h5>
            </div>
            <div class="card-body">
                <div class="chart-container">
                    <canvas id="revenueChart"></canvas>
                </div>
            </div>
        </div>

        <!-- Thống kê theo phương thức thanh toán -->
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">Thống Kê Theo Phương Thức Thanh Toán</h5>
            </div>
            <div class="card-body">
                <button onclick="loadThongKeThanhToan()" class="btn btn-primary mb-3">Xem thống kê</button>
                <div class="row">
                    <div class="col-md-6">
                        <div class="chart-container">
                            <canvas id="paymentMethodChart"></canvas>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="table-responsive">
                            <table class="table table-striped" id="paymentMethodTable">
                                <thead>
                                    <tr>
                                        <th>Phương thức</th>
                                        <th>Số lượng HĐ</th>
                                        <th>Tổng tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        let revenueChart = null;
        let paymentMethodChart = null;

        // Format tiền tệ VND
        function formatVND(value) {
            return new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(value);
        }

        // Hàm tạo biểu đồ doanh thu
        function createRevenueChart(labels, data, type = 'bar', label = 'Doanh Thu') {
            if (revenueChart) {
                revenueChart.destroy();
            }

            const ctx = document.getElementById('revenueChart').getContext('2d');
            revenueChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: label,
                        data: data,
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                        fill: type === 'line' ? 'origin' : undefined
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return formatVND(context.raw);
                                }
                            }
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function(value) {
                                    return formatVND(value);
                                }
                            }
                        }
                    }
                }
            });
        }

        // Hàm tạo biểu đồ phương thức thanh toán
        function createPaymentMethodChart(labels, data) {
            if (paymentMethodChart) {
                paymentMethodChart.destroy();
            }

            const ctx = document.getElementById('paymentMethodChart').getContext('2d');
            paymentMethodChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                        data: data,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.5)',
                            'rgba(54, 162, 235, 0.5)',
                            'rgba(255, 206, 86, 0.5)',
                            'rgba(75, 192, 192, 0.5)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'right'
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    const label = context.label || '';
                                    const value = formatVND(context.raw);
                                    return `${label}: ${value}`;
                                }
                            }
                        }
                    }
                }
            });
        }

        // Load doanh thu theo ngày
        function loadDoanhThuNgay() {
            const startDate = document.getElementById('startDate').value;
            const endDate = document.getElementById('endDate').value;
            
            if (!startDate || !endDate) {
                alert('Vui lòng chọn ngày bắt đầu và kết thúc');
                return;
            }
            
            fetch(`${pageContext.request.contextPath}/thong-ke/ngay?startDate=${startDate}&endDate=${endDate}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    const labels = data.map(item => new Date(item.ngay).toLocaleDateString('vi-VN'));
                    const values = data.map(item => item.doanhThu);
                    createRevenueChart(labels, values, 'line', 'Doanh Thu Theo Ngày');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu: ' + error.message);
                });
        }

        // Load doanh thu theo tháng
        function loadDoanhThuThang() {
            const nam = document.getElementById('namThang').value;
            
            fetch(`${pageContext.request.contextPath}/thong-ke/thang?nam=${nam}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    const labels = data.map(item => `Tháng ${item.thang}`);
                    const values = data.map(item => item.doanhThu);
                    createRevenueChart(labels, values, 'bar', 'Doanh Thu Theo Tháng');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu: ' + error.message);
                });
        }

        // Load thống kê theo phương thức thanh toán
        function loadThongKeThanhToan() {
            fetch(`${pageContext.request.contextPath}/thong-ke/phuong-thuc-thanh-toan`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    // Cập nhật biểu đồ
                    const labels = data.map(item => item.phuongThuc);
                    const values = data.map(item => item.tongTien);
                    createPaymentMethodChart(labels, values);

                    // Cập nhật bảng
                    const tbody = document.getElementById('paymentMethodTable').getElementsByTagName('tbody')[0];
                    tbody.innerHTML = '';
                    data.forEach(item => {
                        const row = tbody.insertRow();
                        row.insertCell(0).textContent = item.phuongThuc;
                        row.insertCell(1).textContent = item.soLuong;
                        row.insertCell(2).textContent = formatVND(item.tongTien);
                    });
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu: ' + error.message);
                });
        }

        // Load dữ liệu mặc định khi trang được tải
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date();
            const lastMonth = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
            
            document.getElementById('startDate').value = lastMonth.toISOString().split('T')[0];
            document.getElementById('endDate').value = today.toISOString().split('T')[0];
            
            loadDoanhThuNgay();
            loadThongKeThanhToan();
        });
    </script>
</body>
</html> 