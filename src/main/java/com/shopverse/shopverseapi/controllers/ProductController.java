package com.shopverse.shopverseapi.controllers;

import com.shopverse.shopverseapi.models.Product;
import com.shopverse.shopverseapi.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){

         List<Product> products =productService.getAllProducts();
         if (products.isEmpty()) {
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product){

        try {
            Product productCreated = productService.createProduct(product);
            return ResponseEntity.ok(productCreated);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        try{
            Product productUpdate = productService.updateProduct(id, product);
            return ResponseEntity.ok(productUpdate);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}
