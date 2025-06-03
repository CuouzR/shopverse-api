package com.shopverse.shopverseapi.service;

import com.shopverse.shopverseapi.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO category);
    CategoryDTO updateCategory(Long id,CategoryDTO category);
    Optional<CategoryDTO> getCategoryById(Long id);
    List<CategoryDTO> getAllCategories();
    void deleteCategoryById(Long id);
    CategoryDTO getCategoryDTOById(Long id);

}
