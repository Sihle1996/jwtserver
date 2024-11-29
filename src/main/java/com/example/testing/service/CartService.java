package com.example.testing.service;

import com.example.testing.entity.CartItem;
import com.example.testing.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;

    // Get all cart items for a user
    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    // Add an item to the cart
    public CartItem addToCart(CartItem cartItem, Long userId) {
        cartItem.setUserId(userId);
        return cartItemRepository.save(cartItem);
    }

    // Remove a specific item from the cart
    public void removeFromCart(Long id) {
        cartItemRepository.deleteById(id);
    }

    // Clear all cart items for a user
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}