package com.kushal.ecommerce.service;

import com.kushal.ecommerce.dto.ImageResponseDTO;
import com.kushal.ecommerce.exception.ResourceNotFoundException;
import com.kushal.ecommerce.mapper.ImageMapper;
import com.kushal.ecommerce.model.Image;
import com.kushal.ecommerce.model.Product;
import com.kushal.ecommerce.repo.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;
    private final ImageMapper imageMapper;

    @Transactional(readOnly = true)
    public Image getImageById(Long id){
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Image Not Found with id: " + id));
    }

    @Transactional(readOnly = true)
    public ImageResponseDTO getImageDTOById(Long id){
        return imageMapper.mapToImageResponseDTO(getImageById(id)); // Reuse above method
    }

    public void deleteImageById(Long id){
        Image image = getImageById(id); // cleaner, throws 404 if not found
        imageRepository.delete(image);
    }

    public List<ImageResponseDTO> addImage(List<MultipartFile> files, Long productId) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("At least one file is required");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product id is required");
        }

        Product product = productService.getProductEntityById(productId);
        List<ImageResponseDTO> result = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("One of the files is empty");
            }

            try {
                Image image = new Image();
                image.setProduct(product);

                String originalName = file.getOriginalFilename();
                image.setFileName(originalName != null ? originalName : "uploaded-file");
                image.setFileType(file.getContentType());
                image.setImage(file.getBytes());

                Image saved = imageRepository.save(image);

                ImageResponseDTO dto = imageMapper.mapToImageResponseDTO(saved);

                result.add(dto);

            } catch (IOException e) {
                throw new IllegalStateException(
                        "Failed to add image for productId=" + productId + ", file=" +
                                (file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown"), e
                );
            }
        }
        return result;
    }

    public void updateImage(MultipartFile file, Long imageId) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        Image image = getImageById(imageId);

        try {
            String name = file.getOriginalFilename();
            image.setFileName(name != null ? name : "uploaded-file");
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());

            imageRepository.save(image);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to update image " + imageId, e);
        }
    }
}
