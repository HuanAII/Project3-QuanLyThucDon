<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.dao.productsDAO" %>
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
    <jsp:include page="menu.jsp" />
    <main>
    
    <%
        String addedMsg = (String) session.getAttribute("addedMsg");
        if (addedMsg != null) {
    %>
        <div id="toast-notification"><%= addedMsg %></div>
    <%
            session.removeAttribute("addedMsg");
        }
    %>


        <div class="breadcrumb">
            <a href="index.html">Trang chủ</a> <span>/</span> Tất cả sản phẩm
        </div>
        
        <!-- Page Title -->
        <h1 class="page-title">TẤT CẢ SẢN PHẨM</h1>
            <form action="FilterProductsServlet" method="post" >
                <div class="filter-section">
                    <div class="sort-buttons">
            <button class="sort-button <%= (request.getAttribute("sortType") == null || "default".equals(request.getAttribute("sortType"))) ? "active" : "" %>" name="sort" value="default">Mặc định</button>

            <button class="sort-button <%= "name-asc".equals(request.getAttribute("sortType")) ? "active" : "" %>" name="sort" value="name-asc">Tên A-Z</button>

            <button class="sort-button <%= "price-asc".equals(request.getAttribute("sortType")) ? "active" : "" %>" name="sort" value="price-asc">Giá thấp đến cao</button>

            <button class="sort-button <%= "price-desc".equals(request.getAttribute("sortType")) ? "active" : "" %>" name="sort" value="price-desc">Giá cao xuống thấp</button>
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
<%
        List<Product> listP = (List<Product>) request.getAttribute("listP");
        if (listP == null) {
            productsDAO dao = new productsDAO();
            listP = dao.getAllProducts();
            request.setAttribute("listP", listP);
            
        }
%>
            <c:forEach items="${listP}" var="o">
                <div class="product-card">
                    <img class="product-image" src="${o.hinhAnh}" alt="Ảnh từ Cloudinary">
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
                                         <i class="fa-solid fa-cart-plus"></i>
                                     </button>
                                </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

    </main>
        <%-- <script src="../assets/js/products.js"></script> --%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
     <script src="${pageContext.request.contextPath}/assets/js/products.js"></script>
</body>
</html>