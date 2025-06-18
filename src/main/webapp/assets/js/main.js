// document
//   .querySelector(".mobile-menu-toggle")
//   .addEventListener("click", function () {
//     const nav = document.querySelector(".horizontal-nav");
//     nav.classList.toggle("active");
//   });

// // Slider functionality
// let currentSlide = 0;
// const slides = document.querySelectorAll(".slide");
// const dots = document.querySelectorAll(".slide-dot");
// const slideCount = slides.length;

// // Set up automatic slideshow
// let slideInterval = setInterval(nextSlide, 5000);

// // Next slide function
// function nextSlide() {
//   slides[currentSlide].classList.remove("active");
//   dots[currentSlide].classList.remove("active");

//   currentSlide = (currentSlide + 1) % slideCount;

//   slides[currentSlide].classList.add("active");
//   dots[currentSlide].classList.add("active");
// }

// // Previous slide function
// function prevSlide() {
//   slides[currentSlide].classList.remove("active");
//   dots[currentSlide].classList.remove("active");

//   currentSlide = (currentSlide - 1 + slideCount) % slideCount;

//   slides[currentSlide].classList.add("active");
//   dots[currentSlide].classList.add("active");
// }

// // Event listeners for arrow controls
// document
//   .querySelector(".slide-arrow.next")
//   .addEventListener("click", function () {
//     clearInterval(slideInterval);
//     nextSlide();
//     slideInterval = setInterval(nextSlide, 5000);
//   });

// document
//   .querySelector(".slide-arrow.prev")
//   .addEventListener("click", function () {
//     clearInterval(slideInterval);
//     prevSlide();
//     slideInterval = setInterval(nextSlide, 5000);
//   });

// // Event listeners for dot controls
// dots.forEach((dot) => {
//   dot.addEventListener("click", function () {
//     clearInterval(slideInterval);
//     const slideIndex = parseInt(this.getAttribute("data-index"));
//     slides[currentSlide].classList.remove("active");
//     dots[currentSlide].classList.remove("active");
//     currentSlide = slideIndex;
//     slides[currentSlide].classList.add("active");
//     dots[currentSlide].classList.add("active");
//     slideInterval = setInterval(nextSlide, 5000);
//   });
// });

document.addEventListener('DOMContentLoaded', function() {
    // Lấy tất cả các phần tử cần thiết
    const slides = document.querySelectorAll('.slide');
    const dots = document.querySelectorAll('.slide-dot');
    const prevArrow = document.querySelector('.slide-arrow.prev');
    const nextArrow = document.querySelector('.slide-arrow.next');
    let currentSlide = 0;
    
    // Hàm chuyển đến slide cụ thể
    function goToSlide(index) {
        // Xóa class active từ tất cả slides và dots
        slides.forEach(slide => slide.classList.remove('active'));
        dots.forEach(dot => dot.classList.remove('active'));
        
        // Thêm class active cho slide và dot hiện tại
        slides[index].classList.add('active');
        dots[index].classList.add('active');
        
        currentSlide = index;
    }
    
    // Chuyển đến slide tiếp theo
    function nextSlide() {
        let nextIndex = currentSlide + 1;
        if (nextIndex >= slides.length) {
            nextIndex = 0; // Quay lại slide đầu tiên nếu đã ở slide cuối
        }
        goToSlide(nextIndex);
    }
    
    // Chuyển đến slide trước đó
    function prevSlide() {
        let prevIndex = currentSlide - 1;
        if (prevIndex < 0) {
            prevIndex = slides.length - 1; // Chuyển đến slide cuối cùng nếu đã ở slide đầu
        }
        goToSlide(prevIndex);
    }
    
    // Xử lý sự kiện click vào nút prev
    prevArrow.addEventListener('click', function() {
        prevSlide();
        resetAutoSlideTimer(); // Reset timer khi người dùng tương tác
    });
    
    // Xử lý sự kiện click vào nút next
    nextArrow.addEventListener('click', function() {
        nextSlide();
        resetAutoSlideTimer(); // Reset timer khi người dùng tương tác
    });
    
    // Xử lý sự kiện click vào các dot
    dots.forEach(dot => {
        dot.addEventListener('click', function() {
            const slideIndex = parseInt(this.getAttribute('data-index'));
            goToSlide(slideIndex);
            resetAutoSlideTimer(); // Reset timer khi người dùng tương tác
        });
    });
    
    // ===== PHẦN TỰ ĐỘNG CHUYỂN SLIDE =====
    
    let autoSlideInterval; // Biến lưu ID của interval
    
    // Hàm bắt đầu chế độ tự động chuyển slide
    function startAutoSlide() {
        // Tự động chuyển slide mỗi 5 giây (5000ms)
        autoSlideInterval = setInterval(function() {
            nextSlide();
        }, 5000);
    }
    
    // Hàm dừng chế độ tự động
    function stopAutoSlide() {
        clearInterval(autoSlideInterval);
    }
    
    // Hàm reset bộ đếm thời gian
    function resetAutoSlideTimer() {
        stopAutoSlide();
        startAutoSlide();
    }
    
    // Dừng tự động chuyển khi người dùng hover chuột vào slider
    const sliderContainer = document.querySelector('.slider-container');
    
    sliderContainer.addEventListener('mouseenter', function() {
        stopAutoSlide();
    });
    
    sliderContainer.addEventListener('mouseleave', function() {
        startAutoSlide();
    });
    
    // Bắt đầu chế độ tự động khi trang được tải
    startAutoSlide();
});