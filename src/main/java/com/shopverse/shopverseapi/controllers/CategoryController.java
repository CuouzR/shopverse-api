package com.shopverse.shopverseapi.controllers;

import com.shopverse.shopverseapi.models.Category;
import com.shopverse.shopverseapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        try {
            Category categoryCreated = categoryService.createCategory(category);
            return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try{
            Category categoryUpdated = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(categoryUpdated);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategoryById(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
