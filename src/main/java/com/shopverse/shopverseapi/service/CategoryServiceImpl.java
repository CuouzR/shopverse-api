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
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }
    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDTO);
    }
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if(categoryDTO.getName() == null || categoryDTO.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (categoryDTO.getDescription() == null || categoryDTO.getDescription().length() < 10 ) {
            throw new IllegalArgumentException("La descripcion debe tener al menos 10 caracteres");
        }

        Category category = toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new IllegalArgumentException("La categoría con ID: "+id+" no existe");
        }
        if (categoryDTO.getDescription() == null || categoryDTO.getDescription().length() < 10 ) {
            throw new IllegalArgumentException("La descripcion debe tener al menos 10 caracteres");
        }
        if(categoryDTO.getName() == null || categoryDTO.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }

        Category categoryToUpdate = categoryOptional.get();
        categoryToUpdate.setName(categoryDTO.getName());
        categoryToUpdate.setDescription(categoryDTO.getDescription());

        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return toDTO(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        return categoryRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }
    public CategoryDTO toDTO(Category category) {
        List<String> productNames = category.getProducts() != null ?
                category.getProducts().stream()
                        .map(Product::getName)
                        .toList() :
                List.of();

        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productNames
        );
    }


    public Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());


        return category;
    }
}
