package com.example.testing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Double totalAmount;
    private String status;
}
