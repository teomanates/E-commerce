package com.turkcelll.ecommerce.mapper;

import com.turkcelll.ecommerce.dto.ProductRequestDto;
import com.turkcelll.ecommerce.dto.ProductResponseDto;
import com.turkcelll.ecommerce.entity.Category;
import com.turkcelll.ecommerce.entity.Product;
import com.turkcelll.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private static CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        ProductMapper.categoryRepository = categoryRepository;
    }

    public static Product toEntity(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        // Kategori eşleştirme
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));
        product.setCategory(category);

        return product;
    }

    public static ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryName("Selam"); // Kategori adı
        return dto;
    }
}
