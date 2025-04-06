document.querySelector('.mobile-menu-toggle').addEventListener('click', function() {
    const nav = document.querySelector('.horizontal-nav');
    nav.classList.toggle('active');
});

// Slider functionality
let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.slide-dot');
const slideCount = slides.length;

// Set up automatic slideshow
let slideInterval = setInterval(nextSlide, 5000);

// Next slide function
function nextSlide() {
    slides[currentSlide].classList.remove('active');
    dots[currentSlide].classList.remove('active');
    
    currentSlide = (currentSlide + 1) % slideCount;
    
    slides[currentSlide].classList.add('active');
    dots[currentSlide].classList.add('active');
}

// Previous slide function
function prevSlide() {
    slides[currentSlide].classList.remove('active');
    dots[currentSlide].classList.remove('active');
    
    currentSlide = (currentSlide - 1 + slideCount) % slideCount;
    
    slides[currentSlide].classList.add('active');
    dots[currentSlide].classList.add('active');
}

// Event listeners for arrow controls
document.querySelector('.slide-arrow.next').addEventListener('click', function() {
    clearInterval(slideInterval);
    nextSlide();
    slideInterval = setInterval(nextSlide, 5000);
});

document.querySelector('.slide-arrow.prev').addEventListener('click', function() {
    clearInterval(slideInterval);
    prevSlide();
    slideInterval = setInterval(nextSlide, 5000);
});

// Event listeners for dot controls
dots.forEach(dot => {
    dot.addEventListener('click', function() {
        clearInterval(slideInterval);
        const slideIndex = parseInt(this.getAttribute('data-index'));  
        slides[currentSlide].classList.remove('active');
        dots[currentSlide].classList.remove('active');
        currentSlide = slideIndex;
        slides[currentSlide].classList.add('active');
        dots[currentSlide].classList.add('active'); 
        slideInterval = setInterval(nextSlide, 5000);
    });

    document.querySelectorAll('.nav-item').forEach(item => {
item.addEventListener('click', function() {
const menuText = this.textContent.trim();

// Xử lý điều hướng dựa trên nội dung của mục menu
if (menuText === 'TRANG CHỦ') {
    window.location.href = 'index.html';
} else if (menuText === 'GIỚI THIỆU') {
    window.location.href = 'about.html';
} else if (menuText === 'SẢN PHẨM' || menuText.includes('SẢN PHẨM')) {
    window.location.href = 'products.html';
} else if (menuText === 'LIÊN HỆ') {
    window.location.href = 'contact.html';
} else if (menuText === 'TIN TỨC') {
    window.location.href = 'news.html';
} else if (menuText === 'HỆ THỐNG CỬA HÀNG') {
    window.location.href = 'stores.html';
} else if (menuText === 'THỰC ĐƠN') {
    window.location.href = 'menu.html';
} else if (menuText === 'ĐẶT BÀN') {
    window.location.href = "booking.html";
} else if (menuText === 'HƯỚNG DẪN SỬ DỤNG') {
    window.location.href = 'guide.html';
} else if (menuText.includes('SẢN PHẨM YÊU THÍCH')) {
    window.location.href = 'favorites.html';
}
});
});
});
//mvn clean compile package