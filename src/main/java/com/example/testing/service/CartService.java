package com.example.testing.service;


import com.example.testing.entity.CartItem;
import com.example.testing.entity.CartItemDTO;
import com.example.testing.entity.MenuItem;
import com.example.testing.repository.CartItemRepository;
import com.example.testing.repository.MenuItemRepository;
import com.example.testing.repository.UserRepository;
import com.example.testing.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public CartItemDTO addItemToCart(Long userId, Long menuItemId, Integer quantity) {
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

        cartItem.setQuantity((cartItem.getQuantity() == null ? 0 : cartItem.getQuantity()) + quantity);
        cartItem.setTotalPrice(menuItem.getPrice() * cartItem.getQuantity());

        cartItem = cartItemRepository.save(cartItem);

        return convertToDTO(cartItem);
    }

    public List<CartItemDTO> getUserCartItems(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CartItemDTO updateCartItem(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getMenuItem().getPrice() * quantity);

        cartItem = cartItemRepository.save(cartItem);

        return convertToDTO(cartItem);
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setMenuItemId(cartItem.getMenuItem().getId());
        dto.setMenuItemName(cartItem.getMenuItem().getName());
        dto.setMenuItemPrice(cartItem.getMenuItem().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setTotalPrice(cartItem.getTotalPrice());
        return dto;
    }
}
