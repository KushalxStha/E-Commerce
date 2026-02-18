package com.kushal.ecommerce.mapper;

import com.kushal.ecommerce.dto.CategoryResponseDTO;
import com.kushal.ecommerce.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponseDTO mapToCategoryResponseDTO(Category category){
        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }
}
