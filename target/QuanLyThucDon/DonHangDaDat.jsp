<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đơn hàng của bạn</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #FF6B35;
            --secondary-color: #2E294E;
            --accent-color: #1B998B;
            --light-bg: #F7F9FC;
            --dark-text: #333333;
            --light-text: #777777;
            --border-radius: 12px;
            --box-shadow: 0 8px 20px rgba(0,0,0,0.08);
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', sans-serif;
            background: var(--light-bg);
            color: #222 !important;
            line-height: 1.6;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 30px 20px;
        }
        
        .page-title {
            text-align: center;
            margin-bottom: 40px;
            color: var(--secondary-color);
            font-size: 32px;
            font-weight: 700;
        }
        
        
        .order-container {
            background: #fff;
            border-radius: var(--border-radius);
            box-shadow: var(--box-shadow);
            margin-bottom: 35px;
            overflow: hidden;
            transition: transform 0.3s ease;
        }
        
        .order-container:hover {
            transform: translateY(-5px);
        }
        
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 25px;
            background: var(--secondary-color);
        }
        
        .order-id {
            font-size: 18px;
            font-weight: bold;
            color: #eee;
        }
        
        .order-date {
            font-size: 14px;
            opacity: 0.9;
            color: #eee;
        }
        
        .order-details {
            padding: 25px;
            border-bottom: 1px solid #eee;
        }
        
        .detail-item {
            display: flex;
            align-items: center;
            margin-bottom: 12px;
        }
        
        .detail-item i {
            color: var(--primary-color);
            margin-right: 12px;
            font-size: 16px;
            width: 20px;
            text-align: center;
        }
        
        .status {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
            margin-left: 10px;
        }
        
        .status-pending {
            background-color: #FFEFD5;
            color: #FF9800;
        }
        
        .status-processing {
            background-color: #E3F2FD;
            color: #2196F3;
        }
        
        .status-delivered {
            background-color: #E8F5E9;
            color: #4CAF50;
        }
        
        .status-cancelled {
            background-color: #FFEBEE;
            color: #F44336;
        }
        
        .order-items {
            padding: 0 25px;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        
        th {
            text-transform: uppercase;
            font-size: 12px;
            font-weight: 600;
            color: var(--light-text);
            padding: 15px 10px;
            text-align: left;
            border-bottom: 2px solid #eee;
        }
        
        td {
            padding: 15px 10px;
            border-bottom: 1px solid #eee;
            vertical-align: middle;
        }
        
        .item-name {
            font-weight: 500;
        }
        
        .item-price, .item-total {
            font-weight: 600;
        }
        
        .order-total {
            display: flex;
            justify-content: flex-end;
            padding: 20px 25px;
            background-color: #f9f9f9;
            font-size: 18px;
            font-weight: 700;
        }
        
        .total-label {
            margin-right: 15px;
            color: var(--light-text);
        }
        
        .total-amount {
            color: var(--primary-color);
        }
        
        .no-orders {
            text-align: center;
            padding: 60px 20px;
            color: var(--light-text);
        }
        
        .no-orders i {
            font-size: 60px;
            color: #ddd;
            margin-bottom: 20px;
        }
        
        @media (max-width: 768px) {
            .container {
                padding: 20px 15px;
            }
            
            .page-title {
                font-size: 24px;
                margin-bottom: 25px;
            }
            
            .order-header {
                flex-direction: column;
                align-items: flex-start;
            }
            
            .order-date {
                margin-top: 5px;
            }
            
            table {
                display: block;
                overflow-x: auto;
            }
            
            .status {
                display: block;
                margin: 8px 0 0 32px;
            }
        }
    </style>
</head>
<body>
    <!-- Nhúng menu điều hướng -->
    <jsp:include page="menu.jsp" />
    
    <div class="container">
        <h1 class="page-title">Đơn hàng của <span>bạn</span></h1>
        
        <c:if test="${empty listDH}">
            <div class="no-orders">
                <i class="fas fa-shopping-bag"></i>
                <h3>Bạn chưa có đơn hàng nào</h3>
                <p>Hãy tiếp tục mua sắm để tạo đơn hàng mới</p>
            </div>
        </c:if>
        
        <c:forEach var="o" items="${listDH}">
            <div class="order-container">
                <div class="order-header">
                    <div class="order-id">Đơn hàng #${o.idDonHang}</div>
                    <div class="order-date"><i class="far fa-calendar-alt"></i> ${o.date}</div>
                </div>
                
                <div class="order-details">
                    <div class="detail-item">
                        <i class="fas fa-user"></i>
                        <div>${o.tenKH}</div>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-phone-alt"></i>
                        <div>${o.sdt}</div>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <div>${o.diaChi}</div>
                    </div>
                    <div class="detail-item">
                        <i class="fas fa-truck"></i>
                        <div>
                            Trạng thái
                            <c:choose>
                                <c:when test="${o.status == 'Đang xử lý'}">
                                    <span class="status status-processing">${o.status}</span>
                                </c:when>
                                <c:when test="${o.status == 'Đang giao hàng'}">
                                    <span class="status status-pending">${o.status}</span>
                                </c:when>
                                <c:when test="${o.status == 'Đã giao hàng'}">
                                    <span class="status status-delivered">${o.status}</span>
                                </c:when>
                                <c:when test="${o.status == 'Đã hủy'}">
                                    <span class="status status-cancelled">${o.status}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="status status-processing">${o.status}</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                
                <div class="order-items">
                    <table>
                        <thead>
                            <tr>
                                <th>Món ăn</th>
                                <th>Số lượng</th>
                                <th>Đơn giá</th>
                                <th>Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ct" items="${o.chiTietList}">
                                <tr>
                                    <td class="item-name">${ct.tenMon}</td>
                                    <td>${ct.soLuong}</td>
                                    <td class="item-price">
                                        <fmt:formatNumber value="${ct.gia}" type="number" groupingUsed="true"/>VNĐ
                                    </td>
                                    <td class="item-total">
                                        <fmt:formatNumber value="${ct.soLuong * ct.gia}" type="number" groupingUsed="true"/>VNĐ
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="order-total">
                    <div class="total-label">Tổng cộng:</div>
                    <div class="total-amount">
                        <fmt:formatNumber value="${o.total}" type="number" groupingUsed="true" />₫
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>
    
    <script>
        // Hiệu ứng đơn giản khi người dùng nhấp vào đơn hàng
        document.querySelectorAll('.order-container').forEach(order => {
            order.addEventListener('click', function() {
                this.style.transition = 'background-color 0.3s ease';
                this.style.backgroundColor = '#f9f9ff';
                setTimeout(() => {
                    this.style.backgroundColor = '#fff';
                }, 300);
            });
        });
    </script>
</body>
</html>