package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
