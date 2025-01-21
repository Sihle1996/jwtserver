package com.example.testing.service;

import com.example.testing.entity.CartItem;
import com.example.testing.entity.MenuItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.MenuItemRepository;
import com.example.testing.repository.UserRepository;
import com.example.testing.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    public CartItem addItemToCart(Long userId, Long menuItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        CartItem cartItem = cartItemRepository.findByUserAndMenuItem(user, menuItem)
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setUser(user);
                    newCartItem.setMenuItem(menuItem);
                    return newCartItem;
                });

        int existingQuantity = (cartItem.getQuantity() != null) ? cartItem.getQuantity() : 0;
        cartItem.setQuantity(existingQuantity + quantity);
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice() * cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }



    public List<CartItem> getUserCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem updateCartItem(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice() * quantity);

        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepository.deleteById(cartItemId);
    }
}

