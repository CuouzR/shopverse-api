package com.shopverse.shopverseapi.controllers;

import com.shopverse.shopverseapi.dto.ProductDTO;
import com.shopverse.shopverseapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductController {


    private final ProductService productService;

    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Obtener todos los productos",
            description = "Este endpoint devuelve una lista con todos los productos disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Lista de productos retornada correctamente")
    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts(){

         List<ProductDTO> products =productService.getAllProducts();
         if (products.isEmpty()) {
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.ok(products);
    }

    @Operation(
            summary = "Obtener un producto por id",
            description = "Este endpoint devuelve un producto por id recibido"
    )
    @ApiResponse(responseCode = "200", description = "Producto retornado correctamente")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(
            summary = "Crear producto",
            description = "Este endpoint crea un producto con la informacion recibida"
    )
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product){

        try {
            ProductDTO productCreated = productService.createProduct(product);
            return ResponseEntity.ok(productCreated);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

    }
    @Operation(
            summary = "Actualizar producto",
            description = "Este endpoint actualiza un producto existente"
    )
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductDTO product){
        try{
            ProductDTO productUpdate = productService.updateProduct(id, product);
            return ResponseEntity.ok(productUpdate);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(
            summary = "Eliminar producto",
            description = "Este endpoint elimina un producto por id recibido"
    )
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.getByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

}
