package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    OrderStatus findByOrderStatusId(Long orderStatusId);
}
