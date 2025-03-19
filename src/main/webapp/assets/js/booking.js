document.addEventListener("DOMContentLoaded", function() {
    // Lấy form đặt bàn
    const bookingForm = document.getElementById("booking-form");

    // Lắng nghe sự kiện submit
    bookingForm.addEventListener("submit", function(event) {
        event.preventDefault(); // Ngăn chặn reload trang

        // Lấy dữ liệu từ form
        const nameInput = document.getElementById("nameInput");
        const phoneInput = document.getElementById("sdt");
        const guests = document.querySelector(".form-control");
        const dateInput = document.getElementById("date");
        const time = document.getElementById("time");
        const messageInput = document.getElementById("message");

        // Kiểm tra dữ liệu nhập vào
        if (!nameInput.value.trim() || !phoneInput.value.trim() || !guests.value || !dateInput.value || !time.value || !messageInput.value.trim()) {
            alert("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra số điện thoại có hợp lệ không (10 chữ số)
        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(phoneInput.value.trim())) {
            alert("Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.");
            return;
        }

        // Hiển thị thông tin trong Console
        console.log("Đặt bàn thành công với thông tin:");
        console.log("Tên:", nameInput.value);
        console.log("Số điện thoại:", phoneInput.value);
        console.log("Số người:", guests.value);
        console.log("Ngày:", dateInput.value);
        console.log("Giờ:", time.value);
        console.log("Ghi chú:", messageInput.value);

        // Hiển thị thông báo thành công
        alert("✅ Đặt bàn thành công! Chúng tôi sẽ liên hệ với bạn sớm nhất.");

        // Reset form
        bookingForm.reset();
    });
});
