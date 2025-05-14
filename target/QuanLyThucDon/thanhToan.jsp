<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <title>Đặt hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/thanhToan.css" />
  </head>
  <body>
    <jsp:include page="menu.jsp" />
    <div class="container">
      <!-- Form bên trái -->
      <div class="form-left">
        <form action="DatHangServlet" method="post">
          <h2>Thông tin nhận hàng</h2>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <div class="thong-bao" style="color: red; font-weight: bold; margin-bottom:10px;"><%= error %></div>
        <%  
        session.removeAttribute("error"); 
        } 
        %>

          <!-- Email: auto fill nếu có -->
          <input type="email" name="email" placeholder="Email" 
                 value="${userInfo.email}" />

          <!-- Họ và tên: bắt buộc nhập -->
          <input type="text" name="hoTen" placeholder="Họ và tên" required 
                value="${userInfo.hoVaTen}"/>

          <!-- Số điện thoại: auto fill nếu có -->
          <input type="tel" name="soDienThoai" placeholder="Số điện thoại" 
                 value="${userInfo.sdt}" />

          <!-- Địa chỉ:  -->
          <input type="text" name="diaChi" placeholder="Địa chỉ nhận hàng" required/>

          <h2>Phương thức thanh toán</h2>
          <div class="payment-method">
            <label>
              <input type="radio" name="payment" value="cod" checked />
              Thanh toán khi nhận hàng (COD)
            </label>
            <label>
              <input type="radio" name="payment" value="bank" />
              Chuyển khoản ngân hàng
            </label>
            <label>
              <input type="radio" name="payment" value="momo" />
              Ví MoMo
            </label>
            <label>
              <input type="radio" name="payment" value="zalopay" />
              Ví ZaloPay
            </label>
          </div>

          <button type="submit" class="btn-order">ĐẶT HÀNG</button>
        </form>
      </div>

      <!-- Giỏ hàng bên phải -->
      <div class="form-right">
        <h2>Đơn hàng</h2>

        <!-- Danh sách món -->
        <c:forEach items="${cart}" var="o">
          <div class="cart-item">
            <img src="${o.hinhAnh}" alt="Món ăn" />
            <div class="item-info">
              <div class="item-name">${o.tenMon}</div>
              <div class="item-price">Đơn giá: ${o.gia} đ</div>
              <div class="item-quantity">Số lượng: ${o.soLuong}</div>
            </div>
          </div>
        </c:forEach>

        <!-- Tóm tắt đơn hàng -->
        <div class="cart-summary">
          <form action="DiscountServlet" method="post">
            <input type="text" placeholder="Nhập mã giảm giá" name="maGiamGia" value="${maGiamGia}" />
            <button type="submit" class="btn-apply-discount">Áp dụng</button>
          </form>

          <div class="total">Tạm tính: <strong>${tongTien} đ</strong></div>
          <div class="total">Đã giảm giá: 
            <strong>
              <c:choose>
                <c:when test="${not empty giamGia}">
                  ${giamGia} đ
                </c:when>
                <c:otherwise>
                  0 đ
                </c:otherwise>
              </c:choose>
            </strong>
          </div>
          <div class="total">Tổng cộng: 
            <strong>
              <c:choose>
                <c:when test="${not empty tongTienSauGiam}">
                  ${tongTienSauGiam} đ
                </c:when>
                <c:otherwise>
                  ${tongTien} đ
                </c:otherwise>
              </c:choose>
            </strong>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
