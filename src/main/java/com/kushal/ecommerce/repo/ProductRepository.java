package com.kushal.ecommerce.repo;

import com.kushal.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByName(String name);
    List<Product> findByBrand(String brand);
    List<Product> findByCategoryNameAndBrand(String category, String brand);
    List<Product> findByBrandAndName(String brand, String name);
    Long countByBrandAndName(String brand, String name);

    // Case-insensitive variants
    List<Product> findByCategoryNameIgnoreCase(String categoryName);
    List<Product> findByNameIgnoreCase(String name);
    List<Product> findByBrandIgnoreCase(String brand);
    List<Product> findByCategoryNameIgnoreCaseAndBrandIgnoreCase(String category, String brand);
    List<Product> findByBrandIgnoreCaseAndNameIgnoreCase(String brand, String name);
    Long countByBrandIgnoreCaseAndNameIgnoreCase(String brand, String name);
}
