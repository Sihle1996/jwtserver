package com.example.testing.controller;

import com.example.testing.entity.Order;
import com.example.testing.principal.UserPrincipal;
import com.example.testing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> getUserOrders(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Fetch user orders
        return orderService.getOrdersByUserId(userPrincipal.getId());
    }

    @PostMapping
    public Order placeOrder(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Place a new order for the user
        return orderService.placeOrder(userPrincipal.getId());
    }
}
