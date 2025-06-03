package com.shopverse.shopverseapi.dto;

public class ProductDTO {


    private String name;
    private Double price;
    private String description;
    private String categoryName;
    private Long categoryId;

    public ProductDTO(){}

    public ProductDTO( String name, Double price,String description, Long categoryId) {

        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }
    public ProductDTO(String name, String description, Double price, Long categoryId, String categoryName) {

        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
