package com.kushal.ecommerce.controller;

import com.kushal.ecommerce.dto.ApiResponseDTO;
import com.kushal.ecommerce.dto.CategoryRequestDTO;
import com.kushal.ecommerce.dto.CategoryResponseDTO;
import com.kushal.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CategoryResponseDTO>>> getAllCategories(){
        List<CategoryResponseDTO> response = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> getCategoryById(@PathVariable Long categoryId){
        CategoryResponseDTO response = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> addCategory(@Valid @RequestBody CategoryRequestDTO request){
        CategoryResponseDTO response = categoryService.addCategory(request);
        return ResponseEntity.status(CREATED)
                .body(new ApiResponseDTO<>("Created!", response));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> updateCategory(@Valid @RequestBody CategoryRequestDTO request,@PathVariable long categoryId){
        CategoryResponseDTO response = categoryService.updateCategory(request, categoryId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Updated!", response));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok(new ApiResponseDTO<>("Deleted!", null));
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> getCategoryByName(@RequestParam String name){
        CategoryResponseDTO response = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(new ApiResponseDTO<>("Success!", response));
    }
}
