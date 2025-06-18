<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="./assets/css/stores.css" />
  </head>
  <body>
     <jsp:include page="menu.jsp" />

    <main>
      <div class="breadcrumb">
        <a href="index.html">Trang chủ</a> <span>/</span> Hệ thống cửa hàng
      </div>
      <h1 class="page-title">HỆ THỐNG CỬA HÀNG</h1>
      <div class="store-container">
        <div class="store-list-container">
          <div class="city-selector">
            <label for="city-select" class="city-label"
              >CHỌN TỈNH THÀNH PHỐ</label
            >
            <select id="city-select" class="city-select">
              <option value="hcm">Hồ Chí Minh</option>
              <option value="hn">Hà Nội</option>
              <option value="dn">Đà Nẵng</option>
            </select>
          </div>

          <div class="store-list" id="store-list">
            <div
              class="store-item active"
              data-address="138A Tô Hiến Thành, Phường 15, Quận 10, Hồ Chí Minh"
              data-lat="10.770088"
              data-lng="106.666819"
            >
              <div class="store-district">QUẬN 10</div>
              <div class="store-address">
                138A Tô Hiến Thành, Phường 15, Quận 10, Hồ Chí Minh, Việt Nam
              </div>
            </div>

            <div
              class="store-item"
              data-address="Bắc Hải, Phường 15, Quận 10, Hồ Chí Minh"
              data-lat="10.767500"
              data-lng="106.663611"
            >
              <div class="store-district">QUẬN 10</div>
              <div class="store-address">
                Bắc Hải, Phường 15, Quận 10, Hồ Chí Minh
              </div>
            </div>

            <div
              class="store-item"
              data-address="215A Lý Thường Kiệt, Phường 15, Quận 11, Hồ Chí Minh"
              data-lat="10.768333"
              data-lng="106.658889"
            >
              <div class="store-district">QUẬN 11</div>
              <div class="store-address">
                215A Lý Thường Kiệt, Phường 15, Quận 11, Hồ Chí Minh, Việt Nam
              </div>
            </div>

            <div
              class="store-item"
              data-address="584, Âu Cơ, Phường 10, Quận Tân Bình, Phường 10, Tân Bình, Hồ Chí Minh"
              data-lat="10.780000"
              data-lng="106.640000"
            >
              <div class="store-district">QUẬN TÂN BÌNH</div>
              <div class="store-address">
                584, Âu Cơ, Phường 10, Quận Tân Bình, Phường 10, Tân Bình, Hồ
                Chí Minh, Việt Nam
              </div>
            </div>

            <div
              class="store-item"
              data-address="615A Âu Cơ, Phú Trung, Tân Phú, Hồ Chí Minh"
              data-lat="10.785000"
              data-lng="106.635000"
            >
              <div class="store-district">QUẬN TÂN PHÚ</div>
              <div class="store-address">
                615A Âu Cơ, Phú Trung, Tân Phú, Hồ Chí Minh, Việt Nam
              </div>
            </div>
          </div>
        </div>

        <!-- Map Section -->
        <div class="map-container" id="map">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.568570417388!2d106.66414307576839!3d10.770140989387621!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752f1b7c3ed289%3A0xa06651894598e488!2zMTM4QSBUw7QgSGnhur9uIFRow6BuaCwgUGjGsOG7nW5nIDE1LCBRdeG6rW4gMTAsIFRow6BuaCBwaOG7kSBI4buTIENow60gTWluaCwgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1647253504321!5m2!1svi!2s"
            allowfullscreen=""
            loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"
          >
          </iframe>
        </div>
      </div>

    </main>

    <script src="./assets/js/stores.js"></script>
  </body>
</html>
