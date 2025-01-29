package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByOrderStatusId(Long id);
}
