document.addEventListener("DOMContentLoaded", function () {
    const bookingForm = document.getElementById("booking-form");

    bookingForm.addEventListener("submit", function (event) {
        event.preventDefault(); 

        const nameInput = document.getElementById("nameInput");
        const phoneInput = document.getElementById("sdt");
        const guests = document.getElementById("guestCount");
        const dateInput = document.getElementById("date");
        const time = document.getElementById("time");
        const messageInput = document.getElementById("message");

        if (!nameInput.value.trim() ||!guests.value|| !phoneInput.value.trim() || !dateInput.value || !time.value || !messageInput.value.trim()) {
            alert("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        const phoneRegex = /^[0-9]{10}$/;
        if (!phoneRegex.test(phoneInput.value.trim())) {
            alert("Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số.");
            return;
        }

        const formData = {
            name: nameInput.value.trim(),
            phone: phoneInput.value.trim(),
            guests: guests.value,
            date: dateInput.value,
            time: time.value,
            message: messageInput.value.trim()
        };

        fetch("BookingTableServlet", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(formData)
        })
        .then(response => response.json()) 
        .then(data => {
           if (data.status ==="success"){
            alert(data.message);
           } else  alert("Có lỗi xảy ra: " + data.message);
            bookingForm.reset();
        })
        .catch(error => {
            console.error("Lỗi khi gửi dữ liệu:", error);
            alert("Có lỗi xảy ra! Vui lòng thử lại.");
        });

        console.log("Đặt bàn thành công với thông tin:");
        console.log(formData);
    }); 
});
