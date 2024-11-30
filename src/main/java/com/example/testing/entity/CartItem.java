package com.example.testing.entity;

import com.example.testing.user.User;
import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    private int quantity;

    public void setUserId(Long userId) {
        this.user = new User();
        this.user.setId(userId); // Set the ID directly
    }

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}
