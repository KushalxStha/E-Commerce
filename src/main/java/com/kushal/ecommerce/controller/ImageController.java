package com.kushal.ecommerce.controller;

import com.kushal.ecommerce.dto.ApiResponseDTO;
import com.kushal.ecommerce.dto.ImageResponseDTO;
import com.kushal.ecommerce.model.Image;
import com.kushal.ecommerce.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{imageId}")
    public ResponseEntity<ApiResponseDTO<ImageResponseDTO>> getImageById(@PathVariable Long imageId){
        ImageResponseDTO response = imageService.getImageDTOById(imageId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<List<ImageResponseDTO>>> saveImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
        List<ImageResponseDTO> response = imageService.addImage(files, productId);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponseDTO<>("Uploaded!",response));
    }


    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponseDTO<Void>> updateImage(@RequestParam MultipartFile file, @PathVariable Long imageId){
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Updated!", null));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteImage(@PathVariable Long imageId){
        imageService.deleteImageById(imageId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Deleted!", null));
    }

    @GetMapping("/{imageId}/download")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }
}
