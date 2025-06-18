<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.example.dao.CartDAO" %>
<%@ page import="com.example.models.CartItem" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/booking.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />
  </head>
  <body>

    <jsp:include page="menu.jsp" />

    <main>

    <%
    String bookingMessage = (String) session.getAttribute("bookingMessage");
    if (bookingMessage != null) {
      %>
          <div id="toast-notification"><%= bookingMessage %></div>
      <%
              session.removeAttribute("bookingMessage"); 
          }
    %>

    <% 
            String user = (String) session.getAttribute("user");
            String account_id = (String) session.getAttribute("account_id");

            if (user != null) {
            System.out.println(">> USER: " + user + " dang xem booking");
            List<CartItem> cartFromDB = CartDAO.getCartByUserId(account_id);
            System.out.println(">> So luong sp trong gio hang: " + cartFromDB.size());

            session.setAttribute("cart", cartFromDB);

        } else {
            System.out.println(">> KHACH chua dang nhap dang xem booking");
            List<CartItem> cartFromSession = (List<CartItem>) session.getAttribute("cart");
            if (cartFromSession == null) cartFromSession = new ArrayList<>();

            System.out.println(">> So luong sp trong gio hang: " + cartFromSession.size());
            session.setAttribute("cart", cartFromSession);
        }
    %>


      

      <div class="breadcrumb">
        <a href="index.html">Trang chủ</a> <span>/</span> Đặt bàn
      </div>
        
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

                <fmt:formatNumber value="${o.gia}" type="currency" currencyCode="VND" />

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
      <div class="booking-container">
        <div class="booking-form">
          <h1 class="booking-title">ĐẶT CHỖ TRỰC TUYẾN</h1>

          <form id="booking-form" action="BookingTableServlet" method="post">
                <div class="form-grid">
                  <div class="form-group">
                    <input type="text" name="name" class="form-control" id="nameInput" placeholder="Tên của bạn" required />
                  </div>
                  <div class="form-group">
                    <input type="tel" name="phone" class="form-control" id="sdt" placeholder="Số điện thoại" required />
                  </div>
                </div>

                <div class="form-grid">
                  <div class="form-group">
                    <select class="form-control" name="guests" id="guestCount" required>
                      <option value="" disabled selected>Số người</option>
                      <option value="1">1 Người</option>
                      <option value="2">2 Người</option>
                      <option value="3">3 Người</option>
                      <option value="4">4 Người</option>
                      <option value="5">5 Người</option>
                      <option value="6">6 Người</option>
                      <option value="7">7 Người</option>
                      <option value="8">8 Người</option>
                      <option value="9">9 Người</option>
                      <option value="10">10+ Người</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <input type="date" name="date" id="date" class="form-control" required />
                  </div>
                </div>

                <div class="form-grid">
                  <div class="form-group">
                    <input type="time" name="time" id="time" class="form-control" required />
                  </div>
                  <div class="form-group"></div>
                </div>

                <div class="form-group">
                  <textarea class="form-textarea" name="message" id="message" placeholder="Tin nhắn" rows="5"></textarea>
                </div>

                <button type="submit" id="submit" class="submit-btn">ĐẶT BÀN</button>
              </form>
        </div>
      </div>
    </main>
    <%-- <script src="./assets/js/booking.js"></script> --%>
    <script>
    // Tự động ẩn thông báo sau 3 giây
    setTimeout(function () {
        var toast = document.getElementById('toast-notification');
        if (toast) {
            toast.style.display = 'none';
        }
    }, 3000);
</script>
  </body>
</html>