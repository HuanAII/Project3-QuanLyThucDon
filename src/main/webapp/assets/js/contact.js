
 document.querySelector('.mobile-menu-toggle').addEventListener('click', function() {
    const nav = document.querySelector('.horizontal-nav');
    nav.classList.toggle('active');
});

document.querySelector('.submit-btn').addEventListener('click', function(e) {
    e.preventDefault();
    
    
    const nameInput = document.querySelector('input[placeholder="Nhập họ và tên"]');
    const emailInput = document.querySelector('input[placeholder="Nhập địa chỉ Email"]');
    const phoneInput = document.querySelector('input[placeholder="Nhập số điện thoại"]');
    const messageInput = document.querySelector('textarea');
    
    if (!nameInput.value || !emailInput.value || !phoneInput.value || !messageInput.value) {
        alert('Vui lòng điền đầy đủ thông tin liên hệ!');
        return;
    }
    alert('Cảm ơn bạn đã gửi tin nhắn. Chúng tôi sẽ liên hệ lại sớm nhất có thể!');
    
    // Reset form
    nameInput.value = '';
    emailInput.value = '';
    phoneInput.value = '';
    messageInput.value = '';
});