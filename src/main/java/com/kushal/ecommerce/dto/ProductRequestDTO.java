package com.kushal.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    @NotBlank
    private String name;
    private String brand;

    @NotNull @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int inventory;
    private String description;

    @NotBlank
    private String category;
}
