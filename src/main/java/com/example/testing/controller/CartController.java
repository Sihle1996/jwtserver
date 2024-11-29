package com.example.testing.controller;

import com.example.testing.entity.CartItem;
import com.example.testing.principal.UserPrincipal;
import com.example.testing.service.CartService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return cartService.getCart(userPrincipal.getId());
    }

    @PostMapping
    public CartItem addToCart(@RequestBody CartItem cartItem, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return cartService.addToCart(cartItem, userPrincipal.getId());
    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
    }

    @DeleteMapping
    public void clearCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.clearCart(userPrincipal.getId());
    }
}