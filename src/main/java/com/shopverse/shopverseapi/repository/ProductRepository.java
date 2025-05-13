package com.shopverse.shopverseapi.repository;

import com.shopverse.shopverseapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
}
