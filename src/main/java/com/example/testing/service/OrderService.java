package com.example.testing.service;

import com.example.testing.entity.CartItem;
import com.example.testing.entity.Order;
import com.example.testing.entity.OrderItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.OrderRepository;
import com.example.testing.user.User;
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

    public List<Order> getOrdersByUser(User user) {
        // Fetch orders associated with the user
        return orderRepository.findByUserId(user.getId());
    }

    @Transactional
    public Order placeOrder(User user) {
        // Fetch cart items using the user's ID
        List<CartItem> cartItems = cartItemRepository.findByUser_Id(user.getId());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot place an order.");
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
        order.setUserId(user.getId());
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus("Pending");

        // Save the order
        order = orderRepository.save(order);

        // Clear the user's cart
        cartItemRepository.deleteByUser_Id(user.getId());

        return order;
    }


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
