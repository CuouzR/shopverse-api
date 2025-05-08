package com.shopverse.shopverseapi.controllers;

import com.shopverse.shopverseapi.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProductController {


    @GetMapping("/products")
    public List<Product> products(){
        return Arrays.asList(
                new Product(1L,"Producto 1"),
                new Product(2L,"Producto 2"),
                new Product(3L,"Producto 3")
        );
    }

}
