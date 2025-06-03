package com.shopverse.shopverseapi.service;

import com.shopverse.shopverseapi.dto.ProductDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    Optional<ProductDTO> getProductById(Long id);
    ProductDTO createProduct(@Valid ProductDTO product);
    ProductDTO updateProduct(Long id,ProductDTO product);
    void deleteProduct(Long id);
    List<ProductDTO> getByCategoryId(Long categoryId);
}
