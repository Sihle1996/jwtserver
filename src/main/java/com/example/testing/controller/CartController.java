package com.example.testing.controller;

import com.example.testing.entity.CartItem;
import com.example.testing.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@RequestParam Long userId) {
        System.out.println("Fetching cart for userId: " + userId);
        List<CartItem> cartItems = cartService.getCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Public endpoint accessible");
    }


    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem cartItem, @RequestParam Long userId) {
        System.out.println("Adding to cart for userId: " + userId + " with item: " + cartItem);
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        CartItem savedCartItem = cartService.addToCart(cartItem, userId);
        return ResponseEntity.ok(savedCartItem);
    }


    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartItemId) {
        // Remove a specific item from the cart
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        // Extract user ID from SecurityContext
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (authentication != null && authentication.getPrincipal() instanceof Long)
                ? (Long) authentication.getPrincipal()
                : null;

        if (userId == null) {
            return ResponseEntity.status(403).build(); // Forbidden if userId is missing
        }

        // Clear the user's cart
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
