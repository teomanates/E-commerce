package com.turkcelll.ecommerce.mapper;

import com.turkcelll.ecommerce.dto.CategoryRequestDto;
import com.turkcelll.ecommerce.dto.CategoryResponseDto;
import com.turkcelll.ecommerce.entity.Category;
import com.turkcelll.ecommerce.entity.Product;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public static Category toEntity(CategoryRequestDto dto, Category parentCategory) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setParentCategory(parentCategory); // Üst kategori
        return category;
    }

    public static CategoryResponseDto toDto(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentCategoryName(category.getParentCategory() != null ? category.getParentCategory().getName() : null);

        // Alt kategorileri ekleme
        dto.setSubCategoryNames(category.getSubCategories() != null
                ? category.getSubCategories().stream().map(Category::getName).collect(Collectors.toList())
                : null);

        // Kategoriye bağlı ürünleri ekleme
        dto.setProductNames(category.getProducts() != null
                ? category.getProducts().stream().map(Product::getName).collect(Collectors.toList())
                : null);

        return dto;
    }
}

