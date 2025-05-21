package com.shopverse.shopverseapi.service;

import com.shopverse.shopverseapi.dto.ProductDTO;
import com.shopverse.shopverseapi.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    List<ProductDTO> getAllProductDTOs();
    Optional<Product> getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id,Product product);
    void deleteProduct(Long id);
    List<ProductDTO> getByCategoryId(Long categoryId);
}
