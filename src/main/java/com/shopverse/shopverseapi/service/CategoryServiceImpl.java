package com.shopverse.shopverseapi.service;

import com.shopverse.shopverseapi.dto.CategoryDTO;
import com.shopverse.shopverseapi.models.Category;
import com.shopverse.shopverseapi.models.Product;
import com.shopverse.shopverseapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category createCategory(Category category) {
        if(category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (category.getDescription().length() < 10 ) {
            throw new IllegalArgumentException("La descripcion debe tener al menos  10 caracteres");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new IllegalArgumentException("El categoryo con ID: "+id+" no existe");
        }
        if (category.getDescription().length() < 10 ) {
            throw new IllegalArgumentException("La descripcion debe tener al menos  10 caracteres");
        }
        if(category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        Category categoryToUpdate = categoryOptional.get();
        categoryToUpdate.setName(category.getName());
        categoryToUpdate.setDescription(category.getDescription());

        return categoryRepository.save(categoryToUpdate);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        List<String> productNames = category.getProducts().stream()
                .map(Product::getName)
                .toList();

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productNames
        );
    }

}
