package com.example.testing.repository;

import com.example.testing.entity.CartItem;
import com.example.testing.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Fetch CartItems by user ID
    List<CartItem> findByUser_Id(Long userId);

    // Delete CartItems by user ID
    @Modifying
    @Transactional
    void deleteByUser_Id(Long userId);
}

