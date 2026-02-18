package com.kushal.ecommerce.mapper;

import com.kushal.ecommerce.dto.ImageResponseDTO;
import com.kushal.ecommerce.model.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    private static final String BASE_URL = "/api/v1/images/";

    public ImageResponseDTO mapToImageResponseDTO(Image image){
        return new ImageResponseDTO(
                image.getId(),
                image.getFileName(),
                image.getFileType(),
                BASE_URL + image.getId() + "/download"
        );
    }
}
