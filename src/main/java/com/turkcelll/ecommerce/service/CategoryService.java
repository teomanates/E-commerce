package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.CategoryRequestDto;
import com.turkcelll.ecommerce.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
     CategoryResponseDto addCategory(CategoryRequestDto dto);
     CategoryResponseDto updateCategory(Long id, CategoryRequestDto dto);
     void deleteCategory(Long id);
     List<CategoryResponseDto> getAllCategories();
     CategoryResponseDto getCategoryById(Long id);
}
