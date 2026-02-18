package com.kushal.ecommerce.mapper;

import com.kushal.ecommerce.dto.ImageResponseDTO;
import com.kushal.ecommerce.dto.ProductResponseDTO;
import com.kushal.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ImageMapper imageMapper;

    public ProductResponseDTO mapToProductResponseDTO(Product product){
        List<ImageResponseDTO> imageDTOs = product.getImages()==null ? List.of():
                product.getImages()
                        .stream()
                        .map(imageMapper::mapToImageResponseDTO)
                        .toList();

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                product.getCategory().getName(),
                imageDTOs
        );
    }
}
