package com.kushal.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private BigDecimal totalAmount;
}
