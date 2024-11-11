package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.CategoryDTO;
import com.tiorico.apptiorico.dtos.ProductDTO;
import com.tiorico.apptiorico.mappers.CategoryMapper;
import com.tiorico.apptiorico.models.Category;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@CrossOrigin("*")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.findActiveCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::toDTO)
                .toList();
        return ResponseEntity.ok(categoryDTOs);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.ok(categoryMapper.toDTO(savedCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDTO) {
        Category categoryToUpdate = categoryMapper.toEntity(categoryDTO);
        categoryToUpdate.setId(id);
        Category updatedCategory = categoryService.updateCategory(categoryToUpdate);
        return ResponseEntity.ok(categoryMapper.toDTO(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        if (category != null) {
            category.setIsActive(false); // Marcado como inactivo
            categoryService.updateCategory(category);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}