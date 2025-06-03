package com.shopverse.shopverseapi.service;

import com.shopverse.shopverseapi.dto.ProductDTO;
import com.shopverse.shopverseapi.models.Category;
import com.shopverse.shopverseapi.models.Product;
import com.shopverse.shopverseapi.repository.CategoryRepository;
import com.shopverse.shopverseapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }



    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::toDTO);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        if(productDTO.getName() == null || productDTO.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }
        Product product = toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("El producto con ID: "+id+" no existe");
        }
        Product productToUpdate = productOptional.get();
        productToUpdate.setName(productDTO.getName());
        productToUpdate.setDescription(productDTO.getDescription());
        productToUpdate.setPrice(productDTO.getPrice());
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
            productToUpdate.setCategory(category);
        }
        Product updatedProduct = productRepository.save(productToUpdate);
        return toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductDTO toDTO(Product product) {
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;

        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                categoryId,
                categoryName
        );
    }
    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
            product.setCategory(category);
        }

        return product;
    }
}
