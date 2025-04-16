<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.productDao" %>
<%@ page import="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tất Cả Sản Phẩm - Ant Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/products.css">


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <header class="header">
        <div class="logo-container">
            <img src="./assets/img/logo.png" alt="Ant Bistro Logo" class="logo">
            <div class="logo-text">ANT BISTRO</div>
        </div>
        <div class="notification-icon">
            <i class="fas fa-bell"></i>
        </div>
        <div class="mobile-menu-toggle">
            <i class="fas fa-bars"></i>
        </div>
    </header>

    <nav class="horizontal-nav">
        <a href="/QuanLyThucDon/user?page=trangchu" class="nav-item">TRANG CHỦ</a>
        <a href="/QuanLyThucDon/user?page=about" class="nav-item">GIỚI THIỆU</a>
        <a href="/QuanLyThucDon/user?page=products" class="nav-item expandable">SẢN PHẨM</a>
        <a href="/QuanLyThucDon/user?page=contact" class="nav-item">LIÊN HỆ</a>
        <a href="/QuanLyThucDon/user?page=stores" class="nav-item">HỆ THỐNG CỬA HÀNG</a>
        <a href="/QuanLyThucDon/user?page=booking" class="nav-item">ĐẶT BÀN</a>
        <a href="/QuanLyThucDon/user?page=favorites" class="nav-item">SẢN PHẨM YÊU THÍCH (0)</a>
    </nav>

    <main>
        <div class="breadcrumb">
            <a href="index.html">Trang chủ</a> <span>/</span> Tất cả sản phẩm
        </div>
        
        <!-- Page Title -->
        <h1 class="page-title">TẤT CẢ SẢN PHẨM</h1>
            <form action="FilterProductsServlet" method="post" >
                <div class="filter-section">
                    <div class="sort-buttons">
                        <button class="sort-button active" name="default"  value="default">Mặc định</button>
                        <button class="sort-button" name="sort" value="name-asc">Tên A-Z</button>
                        <button class="sort-button" name="sort" value="price-asc">Giá thấp đến cao</button>
                        <button class="sort-button" name="sort" value="price-desc">Giá cao xuống thấp</button>
                    </div>
                    
                    <div class="filter-wrapper">
                        <div class="dropdown-filter" id="filter-toggle">
                            Lọc <i class="fas fa-filter"></i>
                        </div>
                        
                        <div class="filter-dropdown" id="filter-dropdown">
    
                            <!-- Giá sản phẩm -->
                            <div class="filter-section">
                                <div class="filter-header">
                                    <div class="filter-title">Giá sản phẩm</div>
                                    <div class="filter-toggle">
                                        <i class="fas fa-minus"></i>
                                    </div>
                                </div>
                                <div class="filter-content">
                                    <div class="filter-option">
                                        <input type="checkbox" name="price" value="under-100" id="price-1">
                                        <label for="price-1">Giá dưới 100.000đ</label>
                                    </div>
                                    <div class="filter-option">
                                        <input type="checkbox" name="price" value="100-300" id="price-2">
                                        <label for="price-2">100.000đ - 300.000đ</label>
                                    </div>
                                    <div class="filter-option">
                                        <input type="checkbox" name="price" value="300-500" id="price-4">
                                        <label for="price-4">300.000đ - 500.000đ</label>
                                    </div>
                                    <div class="filter-option">
                                        <input type="checkbox" name="price" value="over-500" id="price-6">
                                        <label for="price-6">Trên 500.000đ</label>
                                    </div>
                                </div>
                            </div>
    
                            <!-- Loại sản phẩm -->
                            <div class="filter-section">
                                <div class="filter-header">
                                    <div class="filter-title">Loại sản phẩm</div>
                                    <div class="filter-toggle">
                                        <i class="fas fa-minus"></i>
                                    </div>
                                </div>
                                <div class="filter-content">
                                    <div class="filter-option">
                                        <input type="checkbox" name="type" value="food" id="type-buffet" class="filter-checkbox">
                                        <label for="type-buffet" class="filter-label">ĐỒ ĂN</label>
                                    </div>
                                    <div class="filter-option">
                                        <input type="checkbox" name="type" value="drink" id="type-alacarte" class="filter-checkbox">
                                        <label for="type-alacarte" class="filter-label">ĐỒ UỐNGG</label>
                                    </div>
                                </div>
                            </div>
    
                            <!-- Nút ÁP DỤNG -->
                            <div class="filter-actions">
                                <button id="filter-submit" class="filter-submit">ÁP DỤNG</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
 
        <!-- Products Grid -->


        <div class="products-container">
            <c:forEach items="${listP}" var="o">
                <div class="product-card">
                    <img class="product-image" src="QuanLyThucDon/assets/img/mon_an.jpg" alt="Ảnh từ Cloudinary">
                    <div class="product-info">
                        <div class="product-title">${o.tenMon}</div>
                        <div class="product-price">
                            <div class="current-price">${o.gia}₫/1 ${o.donViTinh}</div>
                        </div>
                        <div class="action-buttons">
                            <%-- NÚT THÊM VÀO GIỎ HÀNG --%>
                               <form action="AddToCartServlet" method="post">
                                 <input type="hidden" name="idMon" value="${o.idMon}">
                                     <button type="submit" class="action-button add-to-cart" >
                                         <i class="fas fa-shopping-cart"></i>
                                     </button>
                                </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Chat Icon -->
        <div class="chat-icon">
            <i class="fab fa-facebook-messenger"></i>
        </div>
    </main>
</body>
</html>