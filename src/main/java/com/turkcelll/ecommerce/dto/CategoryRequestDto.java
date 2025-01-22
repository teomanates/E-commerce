package com.turkcelll.ecommerce.dto;


public class CategoryRequestDto {
    private String name;
    private Long parentCategoryId; // Alt kategori için üst kategori ID'si

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}

