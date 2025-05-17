<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thống Kê Doanh Thu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .chart-container {
            position: relative;
            margin: auto;
            width: 80%;
            margin-bottom: 30px;
            height: 400px;
        }
        .filters {
            margin: 20px 0;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
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
        .btn-primary {
            background-color: #0d6efd;
            border-color: #0d6efd;
        }
        .btn-primary:hover {
            background-color: #0b5ed7;
            border-color: #0a58ca;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-4">
        <h1 class="text-center mb-4">Thống Kê Doanh Thu</h1>

        <div class="row">
            <!-- Thống kê theo thời gian -->
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Thống Kê Theo Thời Gian</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="form-label">Thống kê theo ngày:</label>
                                    <input type="date" id="startDate" class="form-control mb-2">
                                    <input type="date" id="endDate" class="form-control mb-2">
                                    <button onclick="loadDoanhThuNgay()" class="btn btn-primary w-100">Xem thống kê</button>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="form-label">Thống kê theo tháng:</label>
                                    <select id="namThang" class="form-control mb-2">
                                        <option value="2024">2024</option>
                                        <option value="2023">2023</option>
                                    </select>
                                    <button onclick="loadDoanhThuThang()" class="btn btn-primary w-100">Xem thống kê</button>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="form-label">Số lượng hiển thị:</label>
                                    <input type="number" id="topLimit" class="form-control mb-2" value="10" min="1">
                                    <div class="d-grid gap-2">
                                        <button onclick="loadTopMonAn()" class="btn btn-primary">Xem Top Món Ăn</button>
                                        <button onclick="loadTopKhachHang()" class="btn btn-primary">Xem Top Khách Hàng</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Biểu đồ doanh thu -->
            <div class="col-md-12">
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
            </div>

            <!-- Biểu đồ top items -->
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Biểu Đồ Chi Tiết</h5>
                    </div>
                    <div class="card-body">
                        <div class="chart-container">
                            <canvas id="topItemsChart"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        let revenueChart = null;
        let topItemsChart = null;

        // Hàm format tiền tệ VND
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

        // Hàm tạo biểu đồ top items
        function createTopItemsChart(labels, data, type = 'pie', label = 'Doanh Thu') {
            if (topItemsChart) {
                topItemsChart.destroy();
            }

            const backgroundColors = [
                'rgba(255, 99, 132, 0.5)',
                'rgba(54, 162, 235, 0.5)',
                'rgba(255, 206, 86, 0.5)',
                'rgba(75, 192, 192, 0.5)',
                'rgba(153, 102, 255, 0.5)',
                'rgba(255, 159, 64, 0.5)',
                'rgba(199, 199, 199, 0.5)',
                'rgba(83, 102, 255, 0.5)',
                'rgba(40, 159, 64, 0.5)',
                'rgba(210, 199, 199, 0.5)'
            ];

            const borderColors = backgroundColors.map(color => color.replace('0.5', '1'));

            const ctx = document.getElementById('topItemsChart').getContext('2d');
            topItemsChart = new Chart(ctx, {
                type: type,
                data: {
                    labels: labels,
                    datasets: [{
                        label: label,
                        data: data,
                        backgroundColor: backgroundColors,
                        borderColor: borderColors,
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
            
            fetch(`/thong-ke/ngay?startDate=${startDate}&endDate=${endDate}`)
                .then(response => response.json())
                .then(data => {
                    const labels = data.map(item => new Date(item.ngay).toLocaleDateString('vi-VN'));
                    const values = data.map(item => item.doanhThu);
                    createRevenueChart(labels, values, 'line', 'Doanh Thu Theo Ngày');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu');
                });
        }

        // Load doanh thu theo tháng
        function loadDoanhThuThang() {
            const nam = document.getElementById('namThang').value;
            
            fetch(`/thong-ke/thang?nam=${nam}`)
                .then(response => response.json())
                .then(data => {
                    const labels = data.map(item => `Tháng ${item.thang}`);
                    const values = data.map(item => item.doanhThu);
                    createRevenueChart(labels, values, 'bar', 'Doanh Thu Theo Tháng');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu');
                });
        }

        // Load top món ăn
        function loadTopMonAn() {
            const limit = document.getElementById('topLimit').value;
            
            fetch(`/thong-ke/top-mon?limit=${limit}`)
                .then(response => response.json())
                .then(data => {
                    const labels = data.map(item => item.tenSanPham);
                    const values = data.map(item => item.doanhThu);
                    createTopItemsChart(labels, values, 'pie', 'Top Món Ăn (Theo Doanh Thu)');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu');
                });
        }

        // Load top khách hàng
        function loadTopKhachHang() {
            const limit = document.getElementById('topLimit').value;
            
            fetch(`/thong-ke/top-khach-hang?limit=${limit}`)
                .then(response => response.json())
                .then(data => {
                    const labels = data.map(item => item.hoTen);
                    const values = data.map(item => item.tongChiTieu);
                    createTopItemsChart(labels, values, 'pie', 'Top Khách Hàng (Theo Chi Tiêu)');
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi tải dữ liệu');
                });
        }

        // Load dữ liệu mặc định khi trang được tải
        document.addEventListener('DOMContentLoaded', function() {
            const today = new Date();
            const lastMonth = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
            
            document.getElementById('startDate').value = lastMonth.toISOString().split('T')[0];
            document.getElementById('endDate').value = today.toISOString().split('T')[0];
            
            loadDoanhThuNgay();
            loadTopMonAn();
        });
    </script>
</body>
</html> 