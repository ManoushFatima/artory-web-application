package com.artory.artory.controller;

import com.artory.artory.dto.OrderRequest;
import com.artory.artory.entity.Order;
import com.artory.artory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        Order savedOrder = orderService.placeOrder(request);
        return ResponseEntity.ok(savedOrder);
    }
}
