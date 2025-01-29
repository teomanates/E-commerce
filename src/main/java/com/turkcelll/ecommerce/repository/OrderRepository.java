package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderId (Long id);
}

