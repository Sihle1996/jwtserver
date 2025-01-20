package com.example.testing.controller;

import com.example.testing.entity.Order;
import com.example.testing.service.OrderService;
import com.example.testing.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getUserOrders(@AuthenticationPrincipal User user) {
        // Fetch orders for the authenticated user
        return orderService.getOrdersByUser(user);
    }

    @PostMapping
    public Order placeOrder(@AuthenticationPrincipal User user) {
        // Place a new order for the authenticated user
        return orderService.placeOrder(user);
    }
}
