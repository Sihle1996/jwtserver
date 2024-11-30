package com.example.testing.service;

import com.example.testing.config.JwtService;
import com.example.testing.entity.CartItem;
import com.example.testing.entity.MenuItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.MenuItemRepository;
import com.example.testing.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    private JwtService jwtService;

    public List<CartItem> getCart(Long userId) {
        // Fetch cart items using the user's ID
        return cartItemRepository.findByUser_Id(userId);
    }

    public CartItem addToCart(CartItem cartItem, HttpServletRequest request) {
        // Extract user ID from the JWT or request attribute
        Long userId = jwtService.extractUserId(request.getHeader("Authorization").substring(7));

        if (userId == null) {
            throw new IllegalStateException("User ID is missing. Ensure you are authenticated.");
        }

        MenuItem menuItem = menuItemRepository.findById(cartItem.getMenuItem().getId())
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));

        cartItem.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        cartItem.setMenuItem(menuItem);

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        // Remove a specific item from the cart
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(Long userId) {
        // Clear all items in the user's cart
        cartItemRepository.deleteByUser_Id(userId);
    }
}
