package com.example.testing.repository;


import com.example.testing.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId); // Fetch all cart items for a user
    void deleteByUserId(Long userId);        // Delete all cart items for a user
}
