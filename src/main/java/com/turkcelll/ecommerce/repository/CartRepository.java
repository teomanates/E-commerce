package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<com.turkcelll.ecommerce.entity.Cart, Long> {
    com.turkcelll.ecommerce.entity.Cart findByUserId(Long userId);
}