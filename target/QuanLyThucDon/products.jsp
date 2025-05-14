<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Product" %>
<%@ page import="com.example.models.Category" %>
<%@ page import="com.example.dao.productsDAO" %>
<%@ page import="com.example.dao.categoryDAO" %>
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
        <%
        List<Product> listP = (List<Product>) request.getAttribute("listP");
        if (listP == null) {
            listP = productsDAO.getAllProducts();
            request.setAttribute("listP", listP);
        }
        List<Category> listC = (List<Category>) request.getAttribute("listC");
        if (listC == null) {
            listC = categoryDAO.getAllCategory();
            request.setAttribute("listC", listC);
        }
        %>
        <!-- Category Tabs -->
<form action="FilterProductsServlet" method="post">
    <div class="products-tabs">
        <c:forEach items="${listC}" var="category">
            <div class="products-tab">
                <!-- Input radio ẩn đi, chỉ hiển thị nhãn -->
                <input type="radio" id="category-${category.id_danhmuc}" name="categoryId" value="${category.id_danhmuc}" class="hidden-radio" onchange="this.form.submit()">
                <label for="category-${category.id_danhmuc}" class="category-label">${category.name_danhmuc}</label>
            </div>
        </c:forEach>
    </div>
</form>




        <!-- Products Grid -->
        <div class="products-container">
            <c:forEach items="${listP}" var="o">
                <div class="product-card">
                    <div class="product-img-wrap">
                        <img class="product-image" src="http://localhost:8080/QuanLyThucDon/${o.hinhAnh}" alt="Hình ảnh món ăn">
                    </div>
                    <div class="product-info">
                        <div class="product-title">${o.tenMon}</div>
                        <div class="product-description">${o.mota}</div>
                        <div class="product-price">${o.gia}₫ <span class="product-unit">/ ${o.donViTinh}</span></div>
                        <form action="AddToCartServlet" method="post" class="action-buttons">
                            <input type="hidden" name="idMon" value="${o.idMon}">
                            <button type="submit" class="action-button add-to-cart" title="Thêm vào giỏ">
                                <i class="fa-solid fa-cart-plus"></i>
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

    </main>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
     <script src="${pageContext.request.contextPath}/assets/js/products.js"></script>
</body>
</html>