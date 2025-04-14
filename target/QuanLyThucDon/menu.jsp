<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tất Cả Sản Phẩm - Ant Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/menu.css">

    <!-- ${pageContext.request.contextPath} -->


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
    <header class="header">
        <div class="logo-container">
            <img src="../assets/img/logo.png" alt="Ant Bistro Logo" class="logo">
            <div class="logo-text">ANT BISTRO</div>
        </div>

            <a href="CartServlet" class="notification-icon">
                <i class="fa-solid fa-cart-shopping"></i>
            </a>
            
        <div class="mobile-menu-toggle">
            <i class="fas fa-bars"></i>
        </div>
    </header>
    
    <nav class="horizontal-nav">
        <div class="nav-item">TRANG CHỦ</div>
        <div class="nav-item">GIỚI THIỆU</div>
        <div class="nav-item expandable active">SẢN PHẨM</div>
        <!-- <div class="nav-item">TIN TỨC</div> -->
        <div class="nav-item">LIÊN HỆ</div>
        <!-- <div class="nav-item">HỆ THỐNG CỬA HÀNG</div> -->
        <div class="nav-item">THỰC ĐƠN</div>
        <div class="nav-item">ĐẶT BÀN</div>
    </nav>