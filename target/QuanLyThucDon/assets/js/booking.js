 document.querySelector('.mobile-menu-toggle').addEventListener('click', function() {
    const nav = document.querySelector('.horizontal-nav');
    nav.classList.toggle('active');
});

// Form submis
document.getElementById('booking-form').addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Get form values
    const formElements = e.target.elements;
    const name = formElements[0].value;
    const phone = formElements[1].value;
    const people = formElements[2].value;
    const date = formElements[3].value;
    const time = formElements[4].value;
    const message = formElements[5].value;
    console.log('Booking details:', { name, phone, people, date, time, message });
    alert('Cảm ơn bạn đã đặt bàn! Chúng tôi sẽ liên hệ với bạn để xác nhận trong thời gian sớm nhất.');
    e.target.reset();
});