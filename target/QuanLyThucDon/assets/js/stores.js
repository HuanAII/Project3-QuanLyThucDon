document.querySelector('.mobile-menu-toggle').addEventListener('click', function() {
    const nav = document.querySelector('.horizontal-nav');
    nav.classList.toggle('active');
});
const storeItems = document.querySelectorAll('.store-item');
storeItems.forEach(item => {
    item.addEventListener('click', function() {
        storeItems.forEach(storeItem => storeItem.classList.remove('active'));
        this.classList.add('active');
        
        // Get store data
        const address = this.getAttribute('data-address');
        const lat = this.getAttribute('data-lat');
        const lng = this.getAttribute('data-lng');
        
        // Update map - this would typically update the iframe src or use Google Maps API
        // For simplicity, we're just logging the data here
        console.log('Selected store:', { address, lat, lng });
        
        // In a real implementation, you would update the map with something like:
        // document.getElementById('map').querySelector('iframe').src = `https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.568570417388!2d${lng}!3d${lat}!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x0!2zMTM4QSBUw7QgSGnhur9uIFRow6BuaCwgUGjGsOG7nW5nIDE1LCBRdeG6rW4gMTAsIFRow6BuaCBwaOG7kSBI4buTIENow60gTWluaCwgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1647253504321!5m2!1svi!2s`;
    });
});

document.getElementById('city-select').addEventListener('change', function() {
    const city = this.value;
    console.log('Selected city:', city);
    if (city === 'hcm') {
    } else if (city === 'hn') {
        alert('Hiện tại chúng tôi chưa có cửa hàng tại Hà Nội. Vui lòng chọn thành phố khác.');
    } else if (city === 'dn') {
        alert('Hiện tại chúng tôi chưa có cửa hàng tại Đà Nẵng. Vui lòng chọn thành phố khác.');
    }
});