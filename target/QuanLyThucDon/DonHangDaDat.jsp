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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/donhangdadat.css">
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