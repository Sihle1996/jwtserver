package com.example.testing.controller;

import com.example.testing.entity.Order;
import com.example.testing.entity.OrderDTO;
import com.example.testing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders") // Removed /v1/auth
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        return new OrderDTO(updatedOrder.getId(), updatedOrder.getTotalAmount(), updatedOrder.getStatus());
    }
}
