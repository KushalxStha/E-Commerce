package com.kushal.ecommerce.service;

import com.kushal.ecommerce.dto.ProductResponseDTO;
import com.kushal.ecommerce.exception.ResourceNotFoundException;
import com.kushal.ecommerce.mapper.ProductMapper;
import com.kushal.ecommerce.model.Category;
import com.kushal.ecommerce.model.Product;
import com.kushal.ecommerce.repo.CategoryRepository;
import com.kushal.ecommerce.repo.ProductRepository;
import com.kushal.ecommerce.dto.ProductRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponseDTO addProduct(ProductRequestDTO request) {
        String categoryName = request.getCategory().trim();

        Category category = Optional.ofNullable(categoryRepository.findByNameIgnoreCase(categoryName))
                .orElseGet(() -> categoryRepository.save(new Category(categoryName)));

        Product saved = new Product(
                request.getName().trim(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
        saved = productRepository.save(saved);
        return productMapper.mapToProductResponseDTO(saved);
    }

    @Transactional
    public ProductResponseDTO updateProduct(ProductRequestDTO request, long productId) {
        String categoryName = request.getCategory().trim();

        Category category = Optional.ofNullable(categoryRepository.findByNameIgnoreCase(categoryName))
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

        Product existingProduct = getProductEntityById(productId);

        existingProduct.setName(request.getName().trim());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        return  productMapper.mapToProductResponseDTO(updatedProduct);
    }

    @Transactional
    public void deleteProductById(long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Product Not Found");
        }
    }

    // For Internal Service Usage
    public Product getProductEntityById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    public ProductResponseDTO getProductById(long id) {
        return productRepository.findById(id)
                .map(productMapper::mapToProductResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapToProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsByName(String name) {
        return productRepository.findByNameIgnoreCase(name)
                .stream()
                .map(productMapper::mapToProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsByCategory(String category) {
        return productRepository.findByCategoryNameIgnoreCase(category)
                .stream()
                .map(productMapper::mapToProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsByBrand(String brand) {
        return productRepository.findByBrandIgnoreCase(brand)
                .stream()
                .map(productMapper::mapToProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameIgnoreCaseAndBrandIgnoreCase(category, brand)
                .stream()
                .map(productMapper::mapToProductResponseDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandIgnoreCaseAndNameIgnoreCase(brand, name)
                .stream()
                .map(productMapper::mapToProductResponseDTO)
                .toList();
    }

    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandIgnoreCaseAndNameIgnoreCase(brand, name);
    }
}
