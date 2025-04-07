<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tất Cả Sản Phẩm - Ant Bistro</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/products.css">
    <!-- ${pageContext.request.contextPath} -->


    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <header class="header">
        <div class="logo-container">
            <img src="../assets/img/logo.png" alt="Ant Bistro Logo" class="logo">
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
        <div class="nav-item">TRANG CHỦ</div>
        <div class="nav-item">GIỚI THIỆU</div>
        <div class="nav-item expandable active">SẢN PHẨM</div>
        <!-- <div class="nav-item">TIN TỨC</div> -->
        <div class="nav-item">LIÊN HỆ</div>
        <!-- <div class="nav-item">HỆ THỐNG CỬA HÀNG</div> -->
        <div class="nav-item">THỰC ĐƠN</div>
        <div class="nav-item">ĐẶT BÀN</div>
        <!-- <div class="nav-item">HƯỚNG DẪN SỬ DỤNG</div> -->
        <div class="nav-item">SẢN PHẨM YÊU THÍCH (0)</div>
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
                                        <label for="type-alacarte" class="filter-label">ĐỒ UỐNG</label>
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
                    <div class="discount-badge">5%</div>
                    <img class="product-image" src="${o.hinhAnh}" alt="Ảnh từ Cloudinary">
                    <div class="product-info">
                        <div class="product-title">${o.tenMon}</div>
                        <div class="product-price">
                            <div class="current-price">${o.gia}₫</div>
                            <div class="original-price">${o.gia}₫</div>
                        </div>
                        <div class="action-buttons">
                            <div class="action-button favorite">
                                <i class="fas fa-link"></i>
                            </div>
                            <div class="action-button add-to-cart">
                                <i class="fas fa-shopping-cart"></i>
                            </div>
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
    
    <!-- Bottom Navigation -->
    <!--
    <nav class="bottom-navbar">
        <a href="#" class="bottom-nav-item">
            <div class="bottom-nav-icon"><i class="fas fa-user"></i></div>
            <div>Tài khoản</div>
        </a>
        <a href="#" class="bottom-nav-item">
            <div class="bottom-nav-icon"><i class="fas fa-search"></i></div>
            <div>Tìm kiếm</div>
        </a>
        <a href="#" class="bottom-nav-item">
            <div class="bottom-nav-icon"><i class="fas fa-shopping-cart"></i></div>
            <div>Giỏ hàng</div>
        </a>
    </nav>
    -->

    <%-- <script src="../assets/js/products.js"></script> --%>
     <script src="${pageContext.request.contextPath}/assets/js/products.js"></script>
</body>
</html>