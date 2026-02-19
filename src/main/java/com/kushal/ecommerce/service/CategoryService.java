package com.kushal.ecommerce.service;

import com.kushal.ecommerce.dto.CategoryResponseDTO;
import com.kushal.ecommerce.exception.AlreadyExistsException;
import com.kushal.ecommerce.exception.ResourceNotFoundException;
import com.kushal.ecommerce.mapper.CategoryMapper;
import com.kushal.ecommerce.model.Category;
import com.kushal.ecommerce.repo.CategoryRepository;
import com.kushal.ecommerce.dto.CategoryRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDTO addCategory(CategoryRequestDTO request) {
        String name = request.getName().trim();

        checkNameConflict(name, null);

        Category category = new Category();
        category.setName(name);

        category = categoryRepository.save(category);
        return categoryMapper.mapToCategoryResponseDTO(category);
    }

    @Transactional
    public CategoryResponseDTO updateCategory(CategoryRequestDTO request, long categoryId) {
        Category existingCategory = getCategoryEntityById(categoryId);
        String newName = request.getName().trim();

        checkNameConflict(newName, categoryId);
        existingCategory.setName(newName);

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.mapToCategoryResponseDTO(updatedCategory);
    }

    @Transactional
    public void deleteCategoryById(long id){
        try{
            categoryRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex){
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }

    // For Internal Service Use
    public Category getCategoryEntityById(long id){
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public CategoryResponseDTO getCategoryById(long id){
        return categoryRepository.findById(id)
                .map(categoryMapper::mapToCategoryResponseDTO)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public CategoryResponseDTO getCategoryByName(String name){
        Category category = categoryRepository.findByNameIgnoreCase(name);
        if(category == null){
            throw new ResourceNotFoundException("Category not found with name: " + name);
        }
        return categoryMapper.mapToCategoryResponseDTO(category);
    }

    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToCategoryResponseDTO)
                .toList();
    }

    // Helper Method
    private void checkNameConflict(String name, Long existingId) {
        Category category = categoryRepository.findByNameIgnoreCase(name);

        if (category != null && (existingId == null || category.getId() != existingId)) {
            throw new AlreadyExistsException("Category already exists: " + name);
        }
    }
}