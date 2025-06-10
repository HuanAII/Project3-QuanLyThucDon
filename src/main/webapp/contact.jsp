<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="./assets/css/contact.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
  </head>
  <body>

    <jsp:include page="menu.jsp" />

    <main>
      <div class="breadcrumb">
        <a href="index.html">Trang chủ</a> <span>/</span> Liên hệ
      </div>
      <h1 class="page-title">LIÊN HỆ</h1>

      <div class="contact-container">
        <!-- Google Map -->
        <div class="map-container">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3834.1627893879353!2d108.22009387413203!3d16.059756684521127!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x314219b4239d8e51%3A0x96e408c6b0419760!2zMzAgxJAuIDIgVGjDoW5nIDksIELDrG5oIEhpw6puLCBI4bqjaSBDaMOidSwgxJDDoCBO4bq1bmcgNTUwMDAw!5e0!3m2!1sen!2s!4v1710255478962!5m2!1sen!2s"
            allowfullscreen=""
            loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"
          ></iframe>

          <div class="map-info">
            <h3>Nhà hàng 23T_Nhat2 - Đà Nẵng</h3>
            <p>30 Đ. 2 Tháng 9, Bình Hiên, Hải Châu, Đà Nẵng 550000</p>
            <a href="#">Xem bản đồ lớn hơn</a>
          </div>
        </div>

        <!-- Contact Info Section -->
        <div class="contact-info-section">
          <div class="contact-info-box">
            <div class="contact-info-icon">
              <i class="fas fa-map-marker-alt"></i>
            </div>
            <h3 class="contact-info-title">ĐỊA CHỈ</h3>
            <div class="contact-info-detail">
              30 Đ. 2 Tháng 9, Bình Hiên<br />
              Hải Châu, Đà Nẵng 550000
            </div>
          </div>

          <div class="contact-info-box">
            <div class="contact-info-icon">
              <i class="fas fa-phone-alt"></i>
            </div>
            <h3 class="contact-info-title">ĐIỆN THOẠI & EMAIL</h3>
            <div class="contact-info-detail">
              <div class="contact-phone">1900 6750</div>
              <div>support@sapo.vn</div>
            </div>
          </div>

          <div class="contact-info-box">
            <div class="contact-info-icon">
              <i class="fas fa-clock"></i>
            </div>
            <h3 class="contact-info-title">GIỜ MỞ CỬA</h3>
            <div class="contact-info-detail">
              Thứ 2 - Chủ nhật<br />
              7h00 - 23h30
            </div>
          </div>
        </div>

        <!-- Contact Form -->
        <div class="contact-form">
          <div class="form-group">
            <label class="form-label">Họ và tên</label>
            <input
              type="text"
              class="form-control"
              placeholder="Nhập họ và tên"
            />
          </div>

          <div class="form-group">
            <label class="form-label">Email</label>
            <input
              type="email"
              class="form-control"
              placeholder="Nhập địa chỉ Email"
            />
          </div>

          <div class="form-group">
            <label class="form-label">Điện thoại</label>
            <input
              type="tel"
              class="form-control"
              placeholder="Nhập số điện thoại"
            />
          </div>

          <div class="form-group">
            <label class="form-label">Nội dung</label>
            <textarea
              class="form-control form-textarea"
              placeholder="Nội dung liên hệ"
            ></textarea>
          </div>

          <button type="submit" class="submit-btn">GỬI TIN NHẮN</button>
        </div>
      </div>

    </main>

    <script src="./assets/js/contact.js"></script>
  </body>
</html>
