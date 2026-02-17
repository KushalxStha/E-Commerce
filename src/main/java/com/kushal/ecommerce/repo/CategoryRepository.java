package com.kushal.ecommerce.repo;

import com.kushal.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByNameIgnoreCase(String name);

    boolean existsByName(String name);
}
