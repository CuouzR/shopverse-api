package com.shopverse.shopverseapi.service;

import com.shopverse.shopverseapi.dto.CategoryDTO;
import com.shopverse.shopverseapi.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id,Category category);
    Optional<Category> getCategoryById(Long id);
    List<Category> getAllCategories();
    void deleteCategoryById(Long id);
    CategoryDTO getCategoryDTOById(Long id);

}
