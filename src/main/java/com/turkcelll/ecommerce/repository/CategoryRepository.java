package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(int Id);
    boolean existsByName(String name); // Benzersiz isim kontrolü için
}
