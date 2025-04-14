// chuyen menu
document.querySelector('.mobile-menu-toggle').addEventListener('click', function() {
    const nav = document.querySelector('.horizontal-nav');
    nav.classList.toggle('active');
  });
  
  
  const filterOptions = document.querySelectorAll('.filter-option');
  filterOptions.forEach(option => {
    option.addEventListener('click', function() {
        filterOptions.forEach(opt => opt.classList.remove('active'));
        this.classList.add('active');
    }); 
  });
  
  
  // Thêm CSS cho cart modal (còn thiếu trong file CSS)
  const additionalCssStyles = `
  .cart-modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 1000;
      opacity: 0;
      visibility: hidden;
      transition: opacity 0.3s, visibility 0.3s;
  }
  
  .cart-modal-overlay.active {
      opacity: 1;
      visibility: visible;
  }
  
  .cart-modal {
      background-color: white;
      width: 90%;
      max-width: 500px;
      border-radius: 8px;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      max-height: 90vh;
  }
  
  .cart-modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px 20px;
      border-bottom: 1px solid #eee;
      background-color: white;
  }
  
  .cart-modal-title {
      font-size: 18px;
      font-weight: bold;
      color: #333;
      text-transform: uppercase;
  }
  
  .cart-modal-close {
      font-size: 24px;
      cursor: pointer;
      color: #333;
  }
  
  .cart-modal-body {
      padding: 20px;
      overflow-y: auto;
      flex-grow: 1;
      background-color: white;
      max-height: 60vh;
  }
  
  .cart-item {
      display: flex;
      margin-bottom: 20px;
      color: #333;
  }
  
  .cart-item-image {
      width: 90px;
      height: 90px;
      object-fit: cover;
      border-radius: 4px;
      margin-right: 15px;
  }
  
  .cart-item-details {
      flex-grow: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
  }
  
  .cart-item-name {
      font-weight: bold;
      margin-bottom: 10px;
      color: #333;
  }
  
  .cart-item-price {
      font-weight: bold;
      color: #ff1236;
      margin-left: auto;
  }
  
  .cart-quantity-control {
      display: flex;
      align-items: center;
      margin-top: 10px;
  }
  
  .cart-quantity-label {
      margin-right: 15px;
      color: #666;
  }
  
  .cart-quantity-buttons {
      display: flex;
      align-items: center;
      border: 1px solid #ddd;
      border-radius: 4px;
      overflow: hidden;
  }
  
  .cart-quantity-btn {
      width: 30px;
      height: 30px;
      background-color: #f5f5f5;
      border: none;
      color: #333;
      font-size: 16px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
  }
  
  .cart-quantity-input {
      width: 40px;
      height: 30px;
      border: none;
      border-left: 1px solid #ddd;
      border-right: 1px solid #ddd;
      text-align: center;
      font-size: 14px;
      color: #333;
  }
  
  .cart-remove-item {
      color: #ff1236;
      text-decoration: none;
      margin-top: 10px;
      display: inline-block;
      cursor: pointer;
  }
  
  .cart-modal-footer {
      padding: 15px 20px;
      border-top: 1px solid #eee;
      display: flex;
      flex-direction: column;
      background-color: white;
  }
  
  .cart-total {
      display: flex;
      justify-content: space-between;
      margin-bottom: 15px;
      font-weight: bold;
      color: #ff1236;
      font-size: 18px;
  }
  
  .cart-checkout-btn {
      background-color: #2c3e50;
      color: white;
      border: none;
      padding: 12px;
      border-radius: 4px;
      cursor: pointer;
      font-weight: bold;
      font-size: 16px;
      transition: background-color 0.2s;
  }
  
  .cart-checkout-btn:hover {
      background-color: #1a2530;
  }
  
  .cart-empty {
      text-align: center;
      padding: 30px 0;
      color: #666;
  }
  
  .cart-empty i {
      font-size: 48px;
      color: #ddd;
      margin-bottom: 15px;
  }
  `;
  
  // Thêm style vào document
  const styleElement = document.createElement('style');
  styleElement.textContent = additionalCssStyles;
  document.head.appendChild(styleElement);
  
  const productsData = [
      {
          id: 1,
          name: "Buffet Ăn Vặt Tokbokki Cho Tín Đồ Mê Món Ăn Hàn Quốc",
          price: 69000,
          originalPrice: 69000,
          discount: 0,
          image: "../assets/img/ptr2.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: false
      },
      {
          id: 2,
          name: "Buffet Trưa Hải Sản Thượng Hạng - Bao Gồm thức uống",
          price: 69000,
          originalPrice: 69000,
          discount: 0,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: false
      },
      {
          id: 3,
          name: "Buffet Xèo Xèo Thịt Nướng Chuẩn Vị Hàn Quốc",
          price: 125000,
          originalPrice: 150000,
          discount: 17,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: true
      },
      {
          id: 4,
          name: "Buffet Lẩu Bò Mỹ, Hải Sản Chuẩn Vị Hàn Quốc",
          price: 145000,
          originalPrice: 145000,
          discount: 0,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: true
      },
      {
          id: 5,
          name: "Buffet Nướng & Lẩu Hơn 130 Món Hải Sản, Bò Mỹ Cùng 5 Vị Lẩu Đặc Biệt",
          price: 229000,
          originalPrice: 315000,
          discount: 28,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: false
      },
      {
          id: 6,
          name: "Japanese BBQ Buffet Nướng Và Lẩu Menu Vip",
          price: 292000,
          originalPrice: 292000,
          discount: 0,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: false
      },
      {
          id: 7,
          name: "Buffet Lẩu Nhật Hải Sản, Bò Mỹ & Dimsum Với 60 Món Nhúng Và 5 Loại Nước Lẩu",
          price: 295000,
          originalPrice: 310000,
          discount: 5,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: false
      },
      {
          id: 8,
          name: "Buffet Nướng Lẩu Thượng Hạng Nhật Bản Hơn 80 Món",
          price: 299000,
          originalPrice: 299000,
          discount: 0,
          image: "../assets/img/ptr3.jpg",
          type: "Buffet",
          brand: "Ant Bistro",
          isNew: true
      }
  ];
  
  // Khởi tạo giỏ hàng
  let cart = [];
  
  // Lấy các phần tử DOM liên quan đến giỏ hàng
  const cartModal = document.getElementById('cartModal');
  const closeCartModal = document.getElementById('closeCartModal');
  const cartItems = document.getElementById('cartItems');
  const cartTotal = document.getElementById('cartTotal');
  const cartCount = document.getElementById('cartCount');
  const checkoutBtn = document.getElementById('checkoutBtn');
  const cartIcon = document.getElementById('cartIcon');
  
  // Hàm hiển thị modal giỏ hàng
  function showCartModal() {
      updateCartDisplay();
      cartModal.classList.add('active');
      document.body.style.overflow = 'hidden'; // Ngăn cuộn trang khi modal hiển thị
  }
  
  // Hàm ẩn modal giỏ hàng
  function hideCartModal() {
      cartModal.classList.remove('active');
      document.body.style.overflow = ''; // Khôi phục cuộn trang
  }
  
  // Cập nhật hiển thị giỏ hàng
  function updateCartDisplay() {
      if (cart.length === 0) {
          cartItems.innerHTML = `
              <div class="cart-empty">
                  <i class="fas fa-shopping-cart"></i>
                  <p>Giỏ hàng của bạn đang trống</p>
              </div>
          `;
      } else {
          cartItems.innerHTML = '';
          cart.forEach((item, index) => {
              const cartItemElement = document.createElement('div');
              cartItemElement.className = 'cart-item';
              cartItemElement.innerHTML = `
                  <img src="${item.image}" alt="${item.name}" class="cart-item-image">
                  <div class="cart-item-details">
                      <div class="cart-item-name">${item.name}</div>
                      <div class="cart-item-price">${(item.price).toLocaleString()}₫</div>
                      <div class="cart-quantity-control">
                          <div class="cart-quantity-label">Số lượng</div>
                          <div class="cart-quantity-buttons">
                              <button class="cart-quantity-btn minus" data-index="${index}">-</button>
                              <input type="text" class="cart-quantity-input" value="${item.quantity}" readonly>
                              <button class="cart-quantity-btn plus" data-index="${index}">+</button>
                          </div>
                      </div>
                      <a class="cart-remove-item" data-index="${index}">Bỏ sản phẩm</a>
                  </div>
              `;
              cartItems.appendChild(cartItemElement);
          });
  
          // Thêm sự kiện cho các nút tăng/giảm số lượng
          document.querySelectorAll('.cart-quantity-btn.minus').forEach(btn => {
              btn.addEventListener('click', function() {
                  const index = parseInt(this.getAttribute('data-index'));
                  if (cart[index].quantity > 1) {
                      cart[index].quantity--;
                      updateCartDisplay();
                      updateCartTotal();
                      saveCartToLocalStorage();
                  }
              });
          });
  
          document.querySelectorAll('.cart-quantity-btn.plus').forEach(btn => {
              btn.addEventListener('click', function() {
                  const index = parseInt(this.getAttribute('data-index'));
                  cart[index].quantity++;
                  updateCartDisplay();
                  updateCartTotal();
                  saveCartToLocalStorage();
              });
          });
  
          // Thêm sự kiện cho nút xóa sản phẩm
          document.querySelectorAll('.cart-remove-item').forEach(btn => {
              btn.addEventListener('click', function() {
                  const index = parseInt(this.getAttribute('data-index'));
                  cart.splice(index, 1);
                  updateCartDisplay();
                  updateCartTotal();
                  saveCartToLocalStorage();
              });
          });
      }
      updateCartTotal();
  }
  
  // Cập nhật tổng giá trị giỏ hàng
  function updateCartTotal() {
      const total = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);
      cartTotal.textContent = `${total.toLocaleString()}₫`;
  }
  
  // Cập nhật số lượng hiển thị trên icon giỏ hàng
  function updateCartCounter() {
      const totalItems = cart.reduce((total, item) => total + item.quantity, 0);
      cartCount.textContent = totalItems;
  }
  
  // Thêm sản phẩm vào giỏ hàng
  function addToCart(product) {
      const existingItemIndex = cart.findIndex(item => item.id === product.id);
      
      if (existingItemIndex !== -1) {
          // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng
          cart[existingItemIndex].quantity++;
      } else {
          // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
          cart.push({
              id: product.id,
              name: product.name,
              price: product.price,
              image: product.image,
              quantity: 1
          });
      }
      
      saveCartToLocalStorage();
      showCartModal();
  }
  
  // Lưu giỏ hàng vào localStorage
  function saveCartToLocalStorage() {
      localStorage.setItem('antBistroCart', JSON.stringify(cart));
      updateCartCounter();
  }
  
  // Lấy giỏ hàng từ localStorage
  function loadCartFromLocalStorage() {
      const savedCart = localStorage.getItem('antBistroCart');
      if (savedCart) {
          cart = JSON.parse(savedCart);
          updateCartCounter();
      }
  }
  
  function displayProducts(products) {
      const productsContainer = document.querySelector('.products-container');
      productsContainer.innerHTML = '';
      if (products.length === 0) {
          productsContainer.innerHTML = '<div class="no-products">Không tìm thấy sản phẩm phù hợp</div>';
          return;
      }
      
      // Tạo HTML 
      products.forEach(product => {
          const productCard = document.createElement('div');
          productCard.className = 'product-card';
          productCard.setAttribute('data-id', product.id);
          productCard.setAttribute('data-price', product.price);
          productCard.setAttribute('data-name', product.name);
          
          let productHTML = `
              ${product.discount > 0 ? `<div class="discount-badge">${product.discount}%</div>` : ''}
              <img src="${product.image}" alt="${product.name}" class="product-image">
              <div class="product-info">
                  <div class="product-title">${product.name}</div>
                  <div class="product-price">
                      <div class="current-price">${product.price.toLocaleString()}₫</div>
                      ${product.discount > 0 ? `<div class="original-price">${product.originalPrice.toLocaleString()}₫</div>` : ''}
                  </div>
                  <div class="action-buttons">
                      <div class="action-button favorite">
                          <i class="fas fa-link"></i>
                      </div>
                      <div class="action-button add-to-cart">
                          <i class="fas fa-shopping-cart"></i>
                      </div>
                  </div>
              </div>
          `;
          
          productCard.innerHTML = productHTML;
          productsContainer.appendChild(productCard);
      });
      
      document.querySelectorAll('.action-button.favorite').forEach(button => {
          button.addEventListener('click', function() {
              alert('Đã thêm vào danh sách yêu thích!');
          });
      });
      
      document.querySelectorAll('.action-button.add-to-cart').forEach(button => {
          button.addEventListener('click', function() {
              const productCard = this.closest('.product-card');
              const productId = parseInt(productCard.getAttribute('data-id'));
              const product = productsData.find(p => p.id === productId);
              
              if (product) {
                  addToCart(product);
              }
          });
      });
  }
  
  // Biến lưu trữ sản phẩm đã được lọc và sắp xếp
  let filteredAndSortedProducts = [...productsData];
  let activeFilters = {
      priceRanges: [],
      brands: [],
      types: []
  };
  let activeSortType = 'default';
  
  // Hàm sắp xếp sản phẩm
  function sortProducts(products, sortType) {
      let sortedProducts = [...products];
      
      switch(sortType) {
          case 'name-asc':
              // Sắp xếp theo tên A-Z
              sortedProducts.sort((a, b) => a.name.localeCompare(b.name));
              break;
          case 'name-desc':
              // Sắp xếp theo tên Z-A
              sortedProducts.sort((a, b) => b.name.localeCompare(a.name));
              break;
          case 'price-asc':
              // Sắp xếp theo giá tăng dần
              sortedProducts.sort((a, b) => a.price - b.price);
              break;
          case 'price-desc':
              // Sắp xếp theo giá giảm dần
              sortedProducts.sort((a, b) => b.price - a.price);
              break;
          case 'newest':
              sortedProducts.sort((a, b) => (b.isNew ? 1 : 0) - (a.isNew ? 1 : 0));
              break;
          default:
              break;
      }
      
      return sortedProducts;
  }
  
  // Hàm lọc sản phẩm theo các tiêu chí
  function filterProducts(products) {
      return products.filter(product => {
          // Lọc theo khoảng giá
          if (activeFilters.priceRanges.length > 0) {
              const matchesPrice = activeFilters.priceRanges.some(range => {
                  switch(range) {
                      case 'price-1': return product.price < 100000;
                      case 'price-2': return product.price >= 100000 && product.price < 200000;
                      case 'price-3': return product.price >= 200000 && product.price < 300000;
                      case 'price-4': return product.price >= 300000 && product.price < 500000;
                      case 'price-5': return product.price >= 500000 && product.price < 1000000;
                      case 'price-6': return product.price >= 1000000;
                      default: return false;
                  }
              });
              
              if (!matchesPrice) return false;
          }
          
          // Lọc theo thương hiệu
          if (activeFilters.brands.length > 0 && !activeFilters.brands.includes(product.brand)){
              return false;
          }
          // Lọc theo loại sản phẩm
          if (activeFilters.types.length > 0 && !activeFilters.types.includes(product.type)){
              return false;
          }
          return true;
      });
  }
  
  function updateProductsDisplay() {
      let filteredProducts = filterProducts(productsData);
      filteredAndSortedProducts = sortProducts(filteredProducts, activeSortType);
      displayProducts(filteredAndSortedProducts);
    //   const resultCount = document.querySelector('.result-count');
    //   if (resultCount) {
    //       resultCount.textContent = `Hiển thị ${filteredAndSortedProducts.length} sản phẩm`;
    //   }
  }
  
  document.addEventListener('DOMContentLoaded', function() {
      // Tải giỏ hàng từ localStorage
      loadCartFromLocalStorage();
      
      // Hiển thị sản phẩm ban đầu
      displayProducts(productsData);
      
      // Xử lý sự kiện cho icon giỏ hàng
      if (cartIcon) {
          cartIcon.addEventListener('click', showCartModal);
      }
      
      // Xử lý sự kiện cho nút đóng giỏ hàng
      if (closeCartModal) {
          closeCartModal.addEventListener('click', hideCartModal);
      }
      
      // Đóng giỏ hàng khi click ra ngoài
      window.addEventListener('click', function(event) {
          if (event.target === cartModal) {
              hideCartModal();
          }
      });
      
      // Xử lý nút thanh toán
      if (checkoutBtn) {
          checkoutBtn.addEventListener('click', function() {
              alert('Chức năng thanh toán đang được phát triển!');
          });
      }
      
      const filterToggle = document.getElementById('filter-toggle');
      const filterDropdown = document.getElementById('filter-dropdown');
      
      if (filterToggle && filterDropdown) {
          filterToggle.addEventListener('click', function() {
              filterDropdown.classList.toggle('active');
          });
          
          document.addEventListener('click', function(event) {
              if (!filterToggle.contains(event.target) && !filterDropdown.contains(event.target)) {
                  filterDropdown.classList.remove('active');
              }
          });
      }
      // Xử lý các mục lọc 
      document.querySelectorAll('.filter-header').forEach(header => {
          header.addEventListener('click', function() {
              const content = this.nextElementSibling;
              const icon = this.querySelector('.filter-toggle i');
              if (icon.classList.contains('fa-minus')) {
                  icon.classList.remove('fa-minus');
                  icon.classList.add('fa-plus');
                  content.style.display = 'none';
              } else {
                  icon.classList.remove('fa-plus');
                  icon.classList.add('fa-minus');
                  content.style.display = 'block';
              }
          });
      });
      
      // Xử lý nút sắp xếp
      document.querySelectorAll('.sort-button').forEach(button => {
          button.addEventListener('click', function() {
              document.querySelectorAll('.sort-button').forEach(btn => {
                  btn.classList.remove('active');
              });
              this.classList.add('active');
              activeSortType = this.getAttribute('data-sort');
              updateProductsDisplay();
          });
      });
      
      // Xử lý checkbox lọc giá
      document.querySelectorAll('.filter-checkbox[id^="price-"]').forEach(checkbox => {
          checkbox.addEventListener('change', function() {
              const priceRangeId = this.id;
              if (this.checked) {
                  if (!activeFilters.priceRanges.includes(priceRangeId)) {
                      activeFilters.priceRanges.push(priceRangeId);
                  }
              } else {     
                  activeFilters.priceRanges = activeFilters.priceRanges.filter(id => id !== priceRangeId);
              }
              updateProductsDisplay();
          });
      });
      
      // Xử lý checkbox lọc thương hiệu
      document.querySelectorAll('.filter-checkbox[id^="brand-"]').forEach(checkbox => {
          checkbox.addEventListener('change', function() {
              const brandId = this.id.replace('brand-', '');  
              if (this.checked) {
                  if (!activeFilters.brands.includes(brandId)) {
                      activeFilters.brands.push(brandId);
                  }
              } else {
                  activeFilters.brands = activeFilters.brands.filter(id => id !== brandId);
              }
              updateProductsDisplay();
          });
      });
      
      // Xử lý checkbox lọc loại sản phẩm
      document.querySelectorAll('.filter-checkbox[id^="type-"]').forEach(checkbox => {
          checkbox.addEventListener('change', function() {
              const typeId = this.id.replace('type-', '');     
              if (this.checked) {
                  if (!activeFilters.types.includes(typeId)) {
                      activeFilters.types.push(typeId);
                  }
              } else {
                  activeFilters.types = activeFilters.types.filter(id => id !== typeId);
              }
              updateProductsDisplay();
          });
      });
      
      addResultCount();
  });
  
  function addResultCount() {
      const productsContainer = document.querySelector('.products-container');
      const resultCountDiv = document.createElement('div');
      resultCountDiv.className = 'result-count';
    //   resultCountDiv.textContent = `Hiển thị ${productsData.length} sản phẩm`;
      resultCountDiv.style.marginBottom = '20px';
      resultCountDiv.style.fontSize = '14px';
      resultCountDiv.style.color = '#ccc';
      if (productsContainer.firstChild) {
          productsContainer.insertBefore(resultCountDiv, productsContainer.firstChild);
      } else {
          productsContainer.appendChild(resultCountDiv);
      }
  }