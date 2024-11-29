package com.example.testing.service;

import com.example.testing.entity.CartItem;
import com.example.testing.entity.Order;
import com.example.testing.entity.OrderItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public List<Order> getOrdersByUserId(Long userId) {
        // Retrieve orders for the specific user
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order placeOrder(Long userId) {
        // Validate cart items
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot place order.");
        }

        // Calculate total amount
        double totalAmount = cartItems.stream()
                .mapToDouble(item -> item.getMenuItem().getPrice() * item.getQuantity())
                .sum();

        // Convert CartItem to OrderItem
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(null, cartItem.getMenuItem(), cartItem.getQuantity()))
                .collect(Collectors.toList());

        // Create and save the order
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");

        // Clear the cart after placing the order
        cartItemRepository.deleteByUserId(userId);
        return orderRepository.save(order);
    }
}
