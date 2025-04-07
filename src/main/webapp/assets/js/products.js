document.addEventListener("DOMContentLoaded", function () {
  // Toggle menu trên mobile
  const mobileMenuToggle = document.querySelector(".mobile-menu-toggle");
  if (mobileMenuToggle) {
    mobileMenuToggle.addEventListener("click", function () {
      const nav = document.querySelector(".horizontal-nav");
      if (nav) {
        nav.classList.toggle("active"); // Toggle menu
      }
    });
  }

  // Hiện / ẩn bảng lọc khi bấm nút "Lọc"
  const filterToggle = document.getElementById("filter-toggle");
  const filterDropdown = document.getElementById("filter-dropdown");

  if (filterToggle && filterDropdown) {
    filterToggle.addEventListener("click", function () {
      filterDropdown.classList.toggle("active"); // Thêm / xóa lớp 'active'
    });
  }
});
