function loadPage(page) {
  window.location.href = `../pages/${page}.html`; // Chuyển hướng toàn trang
}

let currentBackgroundIndex = 0;
const backgrounds = [
  "../assets/images/bg-1.jpeg",
  "../assets/images/bg-2.jpg",
  "../assets/images/bg-3.jpeg",
  "../assets/images/bg-4.jpg",
  "../assets/images/bg-5.jpg",
];

function setBackground(index) {
  const heroElement = document.querySelector(".hero");
  if (heroElement) {
    heroElement.style.backgroundImage = `url(${backgrounds[index]})`;
  } else {
    console.error("Element with class 'hero' not found.");
  }
}

function nextBackground() {
  currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;
  setBackground(currentBackgroundIndex);
}

function prevBackground() {
  currentBackgroundIndex =
    (currentBackgroundIndex - 1 + backgrounds.length) % backgrounds.length;
  setBackground(currentBackgroundIndex);
}

// Tự động chuyển ảnh sau 5 giây
setInterval(nextBackground, 5000);

// Kéo ngang để thay đổi ảnh
let startX = 0;
document.addEventListener("DOMContentLoaded", () => {
  const hero = document.querySelector(".hero");
  if (hero) {
    hero.addEventListener("mousedown", (e) => {
      startX = e.clientX;
    });

    hero.addEventListener("mouseup", (e) => {
      let endX = e.clientX;
      if (startX > endX + 50) {
        nextBackground();
      } else if (startX < endX - 50) {
        prevBackground();
      }
    });

    // Thiết lập ảnh nền ban đầu
    setBackground(currentBackgroundIndex);
  } else {
    console.error("Element with class 'hero' not found.");
  }
});
