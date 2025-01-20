package com.example.testing.service;

import com.example.testing.entity.CartItem;
import com.example.testing.entity.MenuItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.MenuItemRepository;
import com.example.testing.repository.UserRepository;
import com.example.testing.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUser_Id(userId);
    }

    public CartItem addToCart(CartItem cartItem, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        MenuItem menuItem = menuItemRepository.findById(cartItem.getMenuItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        cartItem.setUser(user);
        cartItem.setMenuItem(menuItem);

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUser_Id(userId);
    }
}
