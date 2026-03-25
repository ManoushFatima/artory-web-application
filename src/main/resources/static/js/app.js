// app.js - Core application logic (API integration & localStorage)

// This points to my Spring Boot backend server
const API_BASE_URL = '/api';

// Utility: Format Currency. I added this so prices automatically show up looking like "PKR 5,000"
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-PK', {
        style: 'currency',
        currency: 'PKR',
        maximumFractionDigits: 0
    }).format(amount);
}

// Cart Management using LocalStorage
// I built this object to handle adding and removing items from the cart without needing a database for guests
const CartManager = {
    getCart() {
        // We pull the stringified cart from the browser's local storage and parse it back into a JavaScript array
        const cart = localStorage.getItem('artory_cart');
        return cart ? JSON.parse(cart) : [];
    },

    addItem(product) {
        const cart = this.getCart();
        // Check if the user already added this specific painting to their cart
        const existingItem = cart.find(item => item.id === product.id);

        if (existingItem) {
            existingItem.quantity += 1;
        } else {
            // Use the spread operator to push the new product into the cart array with a starting quantity of 1
            cart.push({
                ...product,
                quantity: 1
            });
        }

        // Save the updated array back into local storage as a string
        localStorage.setItem('artory_cart', JSON.stringify(cart));
        this.updateCartCount();
        this.showNotification(`Added ${product.name} to cart!`);
    },

    removeItem(productId) {
        let cart = this.getCart();
        // Filter out the item the user wants to delete
        cart = cart.filter(item => item.id !== productId);
        localStorage.setItem('artory_cart', JSON.stringify(cart));
        this.updateCartCount();
    },

    clearCart() {
        // Wipes the cart completely after a successful checkout!
        localStorage.removeItem('artory_cart');
        this.updateCartCount();
    },

    getTotalItems() {
        // Reduce the array to get the total number of items, taking quantity into account
        return this.getCart().reduce((total, item) => total + item.quantity, 0);
    },

    getTotalPrice() {
        return this.getCart().reduce((total, item) => total + (item.price * item.quantity), 0);
    },

    updateCartCount() {
        const countElements = document.querySelectorAll('.cart-count');
        const count = this.getTotalItems();
        countElements.forEach(el => {
            el.textContent = count;
            el.style.display = count > 0 ? 'flex' : 'none';
        });
    },

    showNotification(message) {
        // Simple distinct notification
        const notif = document.createElement('div');
        notif.style.position = 'fixed';
        notif.style.bottom = '20px';
        notif.style.right = '20px';
        notif.style.backgroundColor = 'var(--secondary-color)';
        notif.style.color = 'white';
        notif.style.padding = '1rem 2rem';
        notif.style.borderRadius = '0.5rem';
        notif.style.boxShadow = 'var(--shadow-lg)';
        notif.style.zIndex = '9999';
        notif.style.transform = 'translateY(100px)';
        notif.style.opacity = '0';
        notif.style.transition = 'all 0.3s ease';
        notif.textContent = message;

        document.body.appendChild(notif);

        // Animate in
        setTimeout(() => {
            notif.style.transform = 'translateY(0)';
            notif.style.opacity = '1';
        }, 10);

        // Remove after 3 seconds
        setTimeout(() => {
            notif.style.transform = 'translateY(100px)';
            notif.style.opacity = '0';
            setTimeout(() => notif.remove(), 300);
        }, 3000);
    }
};

// API Services
// This object centralizes all communication with my Java backend using the modern Fetch API
const ApiService = {
    async getProducts() {
        try {
            // GET request to fetch the full array of paintings
            const res = await fetch(`${API_BASE_URL}/products`);
            if (!res.ok) throw new Error('Failed to fetch products');
            return await res.json();
        } catch (error) {
            console.error(error);
            return []; // Returns an empty array if the server is off so the site doesn't crash
        }
    },

    async getProduct(id) {
        try {
            // Fetch a specific product by its ID for the product-detail.html page
            const res = await fetch(`${API_BASE_URL}/products/${id}`);
            if (!res.ok) throw new Error('Product not found');
            return await res.json();
        } catch (error) {
            console.error(error);
            return null;
        }
    },

    async placeOrder(orderData) {
        try {
            // POST request sending JSON stringified checkout form data to the server
            const res = await fetch(`${API_BASE_URL}/orders`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(orderData)
            });
            if (!res.ok) throw new Error('Checkout failed');
            return await res.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    },

    async submitContact(contactData) {
        try {
            const res = await fetch(`${API_BASE_URL}/contact`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(contactData)
            });
            if (!res.ok) throw new Error('Failed to submit message');
            return await res.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
};

// Initialize Cart Count + Mobile Menu on load
document.addEventListener('DOMContentLoaded', () => {
    CartManager.updateCartCount();

    // ── Hamburger Menu Toggle ──
    const menuToggle = document.querySelector('.menu-toggle');
    const navLinks = document.querySelector('.nav-links');

    if (menuToggle && navLinks) {
        menuToggle.addEventListener('click', () => {
            menuToggle.classList.toggle('active');
            navLinks.classList.toggle('open');
        });

        // Close menu when a nav link is clicked
        navLinks.querySelectorAll('a').forEach(link => {
            link.addEventListener('click', () => {
                menuToggle.classList.remove('active');
                navLinks.classList.remove('open');
            });
        });
    }
});