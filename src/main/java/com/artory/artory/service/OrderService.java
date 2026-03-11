package com.artory.artory.service;

import com.artory.artory.dto.OrderRequest;
import com.artory.artory.entity.Customer;
import com.artory.artory.entity.Order;
import com.artory.artory.entity.Product;
import com.artory.artory.repository.CustomerRepository;
import com.artory.artory.repository.OrderRepository;
import com.artory.artory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        // Create/Save Customer
        Customer customer = new Customer();
        customer.setName(request.getCustomerName());
        customer.setEmail(request.getCustomerEmail());
        customer.setPhone(request.getCustomerPhone());
        customer.setAddress(request.getCustomerAddress());

        customer = customerRepository.save(customer);

        // Fetch Products and calculate total considering duplicate quantities
        List<Product> distinctProducts = productRepository.findAllById(request.getProductIds());

        List<Product> orderItems = new java.util.ArrayList<>();
        double totalAmount = 0.0;

        for (Long productId : request.getProductIds()) {
            Product product = distinctProducts.stream()
                    .filter(p -> p.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (product != null) {
                orderItems.add(product);
                totalAmount += product.getPrice();
            }
        }

        // Create Order
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setCustomer(customer);
        order.setProducts(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
