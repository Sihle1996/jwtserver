package com.example.testing.controller;

import com.example.testing.entity.Order;
import com.example.testing.entity.OrderDTO;
import com.example.testing.service.OrderService;
import com.example.testing.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder(@AuthenticationPrincipal User authenticatedUser,
                                               @RequestBody Map<String, List<Long>> payload) {
        if (authenticatedUser == null) throw new RuntimeException("User not authenticated");
        List<Long> cartItemIds = payload.get("cartItemIds");
        Order order = orderService.placeOrder(authenticatedUser.getId(), cartItemIds);

        // Convert to DTO for response
        OrderDTO orderDTO = new OrderDTO(order.getId(), order.getTotalAmount(), order.getStatus());
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(@AuthenticationPrincipal User authenticatedUser) {
        if (authenticatedUser == null) throw new RuntimeException("User not authenticated");
        return ResponseEntity.ok(orderService.getOrdersByUser(authenticatedUser.getId()));
    }
}
