package com.turkcelll.ecommerce.rules;

import com.turkcelll.ecommerce.repository.CategoryRepository;
import com.turkcelll.ecommerce.util.type.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void categoryMustExist(Integer id)
    {
        categoryRepository.findById(id).orElseThrow(() -> new BusinessException("Category not found"));
    }
}