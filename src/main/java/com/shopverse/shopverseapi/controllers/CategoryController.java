package com.shopverse.shopverseapi.controllers;

import com.shopverse.shopverseapi.dto.CategoryDTO;
import com.shopverse.shopverseapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "Operaciones relacionadas con categorias")
public class CategoryController {


    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Obtener todas las categorias",
            description = "Este endpoint devuelve una lista con todas las categorias disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada correctamente")
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categories);
    }

    @Operation(
            summary = "Obtener categoria por id",
            description = "Este endpoint devuelve la categoria por id recibido"
    )
    @ApiResponse(responseCode = "200", description = "Categoriaretornada correctamente")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear categoria",
            description = "Este endpoint crea una categoria"
    )
    @ApiResponse(responseCode = "201", description = "Categoria creada correctamente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category){
        try {
            CategoryDTO categoryCreated = categoryService.createCategory(category);
            return new ResponseEntity<>(categoryCreated, HttpStatus.CREATED);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Actualizar categoria",
            description = "Este endpoint actualiza una categoria"
    )
    @ApiResponse(responseCode = "200", description = "Categoria actualizada correctamente")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryDTO category){
        try{
            CategoryDTO categoryUpdated = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(categoryUpdated);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(
            summary = "Elimina una categoria",
            description = "Este endpoint elimina una categoria por id"
    )
    @ApiResponse(responseCode = "204", description = "Categoria eliminada correctamente")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        try{
            categoryService.deleteCategoryById(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable Long id) {
        try {
            CategoryDTO dto = categoryService.getCategoryDTOById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
