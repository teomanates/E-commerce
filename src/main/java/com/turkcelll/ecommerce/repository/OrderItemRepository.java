package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    OrderItem findByOrderItemId(Long id);
}

