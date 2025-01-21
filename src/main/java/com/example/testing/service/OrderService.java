package com.example.testing.service;


import com.example.testing.entity.CartItem;
import com.example.testing.entity.Order;
import com.example.testing.entity.OrderDTO;
import com.example.testing.entity.OrderItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.OrderRepository;
import com.example.testing.repository.UserRepository;
import com.example.testing.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order placeOrder(Long userId, List<Long> cartItemIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (Long cartItemId : cartItemIds) {
            CartItem cartItem = cartItemRepository.findById(cartItemId)
                    .orElseThrow(() -> new RuntimeException("Cart item not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItem(cartItem.getMenuItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItems.add(orderItem);

            totalAmount += cartItem.getTotalPrice();
            cartItemRepository.delete(cartItem);
        }

        Order order = new Order(user, orderItems, totalAmount, LocalDateTime.now(), "PENDING");
        return orderRepository.save(order);
    }

    public List<OrderDTO> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        // Convert Order entities to DTOs
        return orders.stream()
                .map(order -> new OrderDTO(order.getId(), order.getTotalAmount(), order.getStatus()))
                .toList();
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        // Convert Order entities to DTOs
        return orders.stream()
                .map(order -> new OrderDTO(order.getId(), order.getTotalAmount(), order.getStatus()))
                .toList();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
