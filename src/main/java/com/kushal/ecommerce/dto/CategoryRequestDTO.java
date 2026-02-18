package com.kushal.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotBlank(message = "Category Name is required")
    private String name;
}
