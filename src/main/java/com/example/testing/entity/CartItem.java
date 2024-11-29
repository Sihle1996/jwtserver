package com.example.testing.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MenuItem menuItem; // Reference to the MenuItem

    private int quantity; // Quantity of the item

    @Column(nullable = false)
    private Long userId; // Links the cart to a specific user by userId
}