package com.example.testing.controller;

import com.example.testing.entity.Order;
import com.example.testing.entity.OrderDTO;
import com.example.testing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);

        // Convert to DTO for response
        OrderDTO orderDTO = new OrderDTO(updatedOrder.getId(), updatedOrder.getTotalAmount(), updatedOrder.getStatus());
        return ResponseEntity.ok(orderDTO);
    }
}
