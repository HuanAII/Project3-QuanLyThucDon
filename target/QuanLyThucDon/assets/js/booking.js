document.addEventListener("DOMContentLoaded", function() {

    //lay du lieu tu bookingform
    const bookingForm = document.getElementById("booking-form");

    bookingForm.addEventListener("submit", function(event) {
        event.preventDefault(); //ngan trinh duyet tai lai trang

        const nameInput = document.getElementById("nameInput");
        const phoneInput = document.getElementById("sdt");
        const guests = document.querySelector(".form-control");
        const dateInput = document.getElementById("date");
        const time = document.getElementById("time");
        const messageInput = document.getElementById("message");

        if (!nameInput.value.trim() || !phoneInput.value.trim() || !guests.value || !dateInput.value || !time.value || !messageInput.value.trim()) {
            alert("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(phoneInput.value.trim())) {
            alert("Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.");
            return;
        }

        console.log("Đặt bàn thành công với thông tin:");
        console.log("Tên:", nameInput.value);
        console.log("Số điện thoại:", phoneInput.value);
        console.log("Số người:", guests.value);
        console.log("Ngày:", dateInput.value);
        console.log("Giờ:", time.value);
        console.log("Ghi chú:", messageInput.value);

        alert("Dat ban thanh cong");

        bookingForm.reset();
    });
});
