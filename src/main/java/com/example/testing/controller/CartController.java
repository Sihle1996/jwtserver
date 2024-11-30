package com.example.testing.controller;

import com.example.testing.entity.CartItem;
import com.example.testing.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/auth/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCart() {
        // Retrieve the Authentication object
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract userId from authentication details
        Long userId = (Long) authentication.getDetails();

        // Fetch cart items for the user
        List<CartItem> cartItems = cartService.getCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem cartItem, HttpServletRequest request) {
        return ResponseEntity.ok(cartService.addToCart(cartItem, request));
    }

}

