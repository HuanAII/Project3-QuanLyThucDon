<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.User" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tất Cả Sản Phẩm - Ant Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/menu.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

</head>

<%
    String user = (String) session.getAttribute("user");
%>

<body>
    <header class="header">
        <a href="CartServlet" class="notification-icon">
            <i class="fa-solid fa-cart-shopping"></i>
        </a>    

    </header>

    <!-- Thanh điều hướng + Đăng nhập/Đăng ký/Chào -->
    <div class="nav-container">
        <nav class="horizontal-nav">
            <ul class="nav-list">
                    <div class="logo-container" >
                        <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="Ant Bistro Logo" class="logoM">
                    </div>
                    <div class="logo-container" >
                       <img src="" alt="" class="logo">
                    </div>
                <li><a href="/QuanLyThucDon/user?page=trangchu" class="nav-item">TRANG CHỦ</a></li>
                <li><a href="/QuanLyThucDon/user?page=about" class="nav-item">GIỚI THIỆU</a></li>
                <li><a href="/QuanLyThucDon/user?page=products" class="nav-item">THỰC ĐƠN</a></li>
                <li><a href="/QuanLyThucDon/user?page=contact" class="nav-item">LIÊN HỆ</a></li>
                <li><a href="/QuanLyThucDon/user?page=stores" class="nav-item">HỆ THỐNG CỬA HÀNG</a></li>
                <li><a href="/QuanLyThucDon/user?page=booking" class="nav-item">ĐẶT BÀN</a></li>
                <% if (user != null) { %>
                    <li><a href="${pageContext.request.contextPath}/DonHangServlet" class="nav-item">ĐƠN HÀNG CỦA BẠN</a></li>
                <% } %>
            </ul>

            <div class="user-actions">
                <% if (user != null) { %>
                   <a href="${pageContext.request.contextPath}/profile"> <span class="welcome-text nav-item"><%= user %></span></a>
                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="nav-item">Đăng xuất</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/login.jsp" class="nav-item">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/register.jsp" class="nav-item">Đăng ký</a>
                <% } %>
            </div>
        </nav>
    </div>
</body>
</html>
