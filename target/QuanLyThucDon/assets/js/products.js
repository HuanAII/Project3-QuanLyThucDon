// Toggle menu trên mobile
document.addEventListener("DOMContentLoaded", function () {
  const menuToggle = document.querySelector(".mobile-menu-toggle");
  if (menuToggle) {
    menuToggle.addEventListener("click", function () {
      const nav = document.querySelector(".horizontal-nav");
      nav.classList.toggle("active");
    });
  }

  // Toggle dropdown lọc sản phẩm
  const filterToggle = document.getElementById("filter-toggle");
  const filterDropdown = document.getElementById("filter-dropdown");

  if (filterToggle && filterDropdown) {
    filterToggle.addEventListener("click", function () {
      filterDropdown.classList.toggle("show"); // Thêm / gỡ class 'show' để hiện dropdown
    });
  }

  // Đánh dấu active cho filter-option được chọn
  const filterOptions = document.querySelectorAll(
    ".filter-option input[type='checkbox']"
  );
  filterOptions.forEach((option) => {
    option.addEventListener("change", function () {
      // Có thể highlight label nếu muốn, ví dụ:
      const label = this.nextElementSibling;
      if (this.checked) {
        label.classList.add("active");
      } else {
        label.classList.remove("active");
      }
    });
  });
});

window.addEventListener("DOMContentLoaded", () => {
  const toast = document.getElementById("toast-notification");
  if (toast) {
    setTimeout(() => {
      toast.style.opacity = 0;
    }, 7000);
  }
});
