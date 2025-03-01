// load page
function loadPage(page) {
  document.getElementById("contentFrame").src = `./${page}.html`;
}

// menu
document.addEventListener("DOMContentLoaded", function () {
  const menuBtn = document.getElementById("menuBtn");
  const menuIcon = document.querySelector(".menu-icon");

  menuBtn.addEventListener("click", function (event) {
    event.stopPropagation();
    menuIcon.classList.toggle("active");
  });

  document.addEventListener("click", function (event) {
    if (!menuIcon.contains(event.target)) {
      menuIcon.classList.remove("active");
    }
  });
});
