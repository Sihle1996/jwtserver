package com.example.testing.entity;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long menuItemId;
    private String menuItemName;
    private Double menuItemPrice;
    private Integer quantity;
    private Double totalPrice;
}
