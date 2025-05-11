<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ant Bistro - Nhà hàng Ant Bistro</title>
    <link rel="stylesheet" href="./assets/css/styles.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
    />
  </head>
  <body>
    <jsp:include page="menu.jsp" />

    <main class="main-content">
      <div class="slider-container">
        <div class="slide active">
          <img
            src="./assets/img/bg-1.jpeg"
            alt="Sushi Dish"
            class="hero-image"
          />
        </div>
        <div class="slide">
          <img
            src="./assets/img/bg-2.jpg"
            alt="Japanese Cuisine"
            class="hero-image"
          />
        </div>
        <div class="slide">
          <img
            src="./assets/img/bg-3.jpeg"
            alt="Chef Special"
            class="hero-image"
          />
        </div>
        <div class="slide">
          <img
            src="./assets/img/bg-4.jpg"
            alt="Restaurant Interior"
            class="hero-image"
          />
        </div>

        <!-- Slide Navigation -->
        <div class="slide-arrow prev">
          <i class="fas fa-chevron-left"></i>
        </div>
        <div class="slide-arrow next">
          <i class="fas fa-chevron-right"></i>
        </div>

        <div class="slide-nav">
          <div class="slide-dot active" data-index="0"></div>
          <div class="slide-dot" data-index="1"></div>
          <div class="slide-dot" data-index="2"></div>
          <div class="slide-dot" data-index="3"></div>
        </div>

        <div class="overlay">
          <div class="overlay-logo">
            <i
              class="fas fa-utensils"
              style="font-size: 28px; color: #ff1236"
            ></i>
          </div>
          <div class="overlay-title">ANT BISTRO</div>
          <h1 class="restaurant-name">Nhà hàng Ant Bistro</h1>
          <button class="about-btn">VỀ CHÚNG TÔI</button>

          <!-- Footer -->
          <div class="footer">
            © Bản quyền thuộc về Kiến Vàng | Cung cấp bởi Sapo
          </div>
        </div>
      </div>

      <div class="chat-icon">
        <i class="fab fa-facebook-messenger"></i>
      </div>
    </main>
  </body>
  <script src="./assets/js/main.js"></script>
</html>
