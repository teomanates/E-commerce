package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.CategoryRequestDto;
import com.turkcelll.ecommerce.dto.CategoryResponseDto;
import com.turkcelll.ecommerce.entity.Category;
import com.turkcelll.ecommerce.mapper.CategoryMapper;
import com.turkcelll.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto dto) {
        // Üst kategori eşleştirme
        Category parentCategory = dto.getParentCategoryId() != null
                ? categoryRepository.findById(dto.getParentCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Üst kategori bulunamadı!"))
                : null;

        Category category = CategoryMapper.toEntity(dto, parentCategory);
        Category savedCategory = categoryRepository.save(category);

        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));

        category.setName(dto.getName());

        // Üst kategori güncelleme
        if (dto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Üst kategori bulunamadı!"));
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }

        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));

        if (!category.getProducts().isEmpty()) {
            throw new IllegalStateException("Bu kategoriye bağlı ürünler olduğu için silinemez!");
        }

        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));
        return CategoryMapper.toDto(category);
    }
}
