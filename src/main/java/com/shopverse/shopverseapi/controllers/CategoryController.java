package com.shopverse.shopverseapi.controllers;

import com.shopverse.shopverseapi.models.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    @GetMapping
    public List<Category> getAllCategories() {

        return Arrays.asList(
                new Category(1L, "Tecnología", "Productos electrónicos y computación"),
                new Category(2L, "Hogar", "Artículos para el hogar y decoración"),
                new Category(3L, "Ropa", "Indumentaria y accesorios")

        );

    }
}
