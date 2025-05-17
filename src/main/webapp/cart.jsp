<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
  </head>
  <body>
    <%-- Menu Header --%>
    <jsp:include page="menu.jsp" />

    <main>
      <%-- THONG BAO --%>
    <% String thongBao = (String) request.getAttribute("thongBao"); %>
    <% if (thongBao != null) { %>
        <div class="thong-bao" style="color: red; font-weight: bold;"><%= thongBao %></div>
    <%  
    session.removeAttribute("thongBao"); 
    } 
    %>

      <div class="cart-container">

          <c:forEach items="${cart}" var="o">
            <div class="cart-item">
              <img src="http://localhost:8080/QuanLyThucDon/uploads/${o.hinhAnh}" alt="ảnh món ăn" />
              
              <div class="info">
                <h3>${o.tenMon}</h3>
                <span class="remove">
                  <form action="UpdateCartServlet" method="get" style="display:inline;">
                    <input type="hidden" name="idMon" value="${o.idMon}" />
                    <button type="submit" name="action" value="remove" style="color:red; background:none; border:none; cursor:pointer;">🗑 Xoá</button>
                  </form>
                </span>
              </div>

                <div class="price">
                    <fmt:formatNumber value="${o.gia}" type="number" groupingUsed="true" /> VND
                </div>


              <div class="quantity">
                <form action="UpdateCartServlet" method="get" style="display:flex; align-items:center;">
                  <input type="hidden" name="idMon" value="${o.idMon}" />
                  <button type="submit" name="action" value="minus">-</button>
                  <input type="text" value="${o.soLuong}" readonly style="width: 30px; text-align:center; margin: 0 5px;" />
                  <button type="submit" name="action" value="plus">+</button>
                </form>
              </div>
            </div>
          </c:forEach>


      </div>

      <%-- Tóm tắt giỏ hàng --%>
      <div class="summary">
        <a href="products.jsp" class="continue">← Tiếp tục mua hàng</a>
        <div class="total">
          <p>Tạm tính: <strong><fmt:formatNumber value="${tongTien}" type="number" groupingUsed="true" /> VNĐ</strong></p>
          <p>Thành tiền: <strong class="highlight"><fmt:formatNumber value="${tongTien}" type="number" groupingUsed="true" /> VND</strong></p>
          <form action="ThanhToanServlet" method="post">
          <input type="hidden" name="thanhtoan" />
          <button class="checkout">THANH TOÁN NGAY</button>
          </form>
        </div>
      </div>
    </main>
  </body>
</html>
