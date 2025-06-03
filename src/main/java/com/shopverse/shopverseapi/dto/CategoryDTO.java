package com.shopverse.shopverseapi.dto;


import java.util.List;

public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    private List<String> productNames;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, String description, List<String> productNames) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.productNames = productNames;
    }
    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
