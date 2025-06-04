<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Giới Thiệu - Ant Bistro</title>
    <link rel="stylesheet" href="./assets/css/about.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
  </head>
  <body>
    
    <jsp:include page="menu.jsp" />

    <!-- Main Content -->
    <main>
      <div class="breadcrumb">
        <a href="index.html">Trang chủ</a> <span>/</span> Giới thiệu
      </div>
      <h1 class="page-title">GIỚI THIỆU</h1>
      <!-- About Content -->
      <div class="about-content">
        <div class="about-text">
          <p>
            <span class="bold">23T_Nhat2</span> là một sản phẩm trực thuộc Ant
            Group – doanh nghiệp đi đầu trong lĩnh vực thương mại điện tử Việt
            Nam.
          </p>

          <p>
            Được thành lập vào cuối năm 2010, cho tới nay
            <span class="highlight">23T_Nhat2</span> đã có
            <span class="highlight">500</span> nhân viên làm việc tại
            <span class="highlight">02</span> thành phố lớn là Đà Nẵng và Tp. Hồ
            Chí Minh phục vụ trên <span class="highlight">01 triệu</span> khách
            hàng trên toàn quốc.
          </p>

          <p>
            <span class="bold">Ant Bistro</span> hiện đang cung cấp cho khách
            hàng trải nghiệm mua sắm những đợt giảm giá theo nhóm (groupon) của
            các doanh nghiệp, cam kết về uy tín chất lượng dịch vụ và đội ngũ
            nhân viên chuyên nghiệp tận tình đã giúp
            <span class="highlight">Ant Bistro</span> bán thành công gần
            <span class="highlight">5 triệu</span> đơn hàng cho hàng ngàn đối
            tác và đang giữ kỷ lục về số lượng bán các cơ hội giảm giá của hệ
            thống Ant Movie, chuỗi nhà hàng Ant Bistro, khu vui chơi Ant Kids và
            nhiều đối tác khác.
          </p>

          <p>
            <span class="bold">Ant Bistro</span> luôn nỗ lực để trở thành cầu
            nối nhanh và hiệu quả nhất giúp doanh nghiệp tiếp cận người tiêu
            dùng với phương châm <span class="highlight">"Happy to Buy"</span>.
            Hãy truy cập vào <span class="bold">Ant Bistro</span> mỗi ngày để có
            những giây phút mua sắm vui vẻ!
          </p>
        </div>
      </div>

      <!-- Contact Section -->
      <section class="contact-section">
        <div class="section-heading">ANT BISTRO</div>
        <h2 class="contact-title">THÔNG TIN LIÊN HỆ</h2>

        <div class="contact-grid">
          <div class="contact-box">
            <i class="fas fa-map-marker-alt fa-2x"></i>
            <h3>ĐỊA CHỈ</h3>
            <p>30 Đ. 2 Tháng 9, Bình Hiên, Hải Châu, Đà Nẵng 550000</p>
          </div>

          <div class="contact-box">
            <i class="fas fa-phone-alt fa-2x"></i>
            <h3>HOTLINE & EMAIL</h3>
            <div class="contact-phone">1900 6750</div>
            <div class="contact-email">support@sapo.vn</div>
          </div>

          <div class="contact-box">
            <i class="fas fa-clock fa-2x"></i>
            <h3>HỖ TRỢ KHÁCH HÀNG</h3>
            <p>Thứ 2 - Chủ nhật<br />7h00 - 23h30</p>
          </div>
        </div>

        <!-- Social Media -->
        <div class="social-media">
          <a href="#" class="social-icon"><i class="fab fa-facebook-f"></i></a>
          <a href="#" class="social-icon"><i class="fab fa-twitter"></i></a>
          <a href="#" class="social-icon"><i class="fab fa-youtube"></i></a>
          <a href="#" class="social-icon"><i class="fab fa-instagram"></i></a>
        </div>
      </section>

      <!-- Footer -->
      <footer class="footer">
        © Bản quyền thuộc về Kiến Vàng | Cung cấp bởi Sapo
      </footer>


    </main>

    <!-- <script src="./assets/js/about.js"></script> -->
  </body>
</html>
