package com.kushal.ecommerce.controller;

import com.kushal.ecommerce.dto.ApiResponseDTO;
import com.kushal.ecommerce.dto.ProductRequestDTO;
import com.kushal.ecommerce.dto.ProductResponseDTO;
import com.kushal.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProducts(){
        List<ProductResponseDTO> response = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProductById(@PathVariable Long productId){
        ProductResponseDTO response = productService.getProductById(productId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> addProduct(@Valid @RequestBody ProductRequestDTO request){
        ProductResponseDTO response = productService.addProduct(request);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponseDTO<>("Added!", response));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> updateProduct(@Valid @RequestBody ProductRequestDTO request, @PathVariable Long productId){
        ProductResponseDTO response = productService.updateProduct(request, productId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Updated!", response));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> deleteProduct(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Deleted!", null));
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductByName(@RequestParam String name){
        List<ProductResponseDTO> response = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/by-brand")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductByBrand(@RequestParam String brand){
        List<ProductResponseDTO> response = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/by-category")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductBy(@RequestParam String category){
        List<ProductResponseDTO> response = productService.getProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/filter/by-category-brand")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        List<ProductResponseDTO> response = productService.getProductsByCategoryAndBrand(category, brand);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/filter/by-brand-name")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductByBrandAndName(@RequestParam String brand, @RequestParam String product){
        List<ProductResponseDTO> response = productService.getProductsByBrandAndName(brand, product);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/count/by-brand-name")
    public ResponseEntity<ApiResponseDTO<Long>> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String product){
        Long countResponse = productService.countProductsByBrandAndName(brand, product);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", countResponse));
    }
}
