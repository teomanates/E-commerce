package com.turkcell.ecommerce.repository;

import com.turkcell.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByOrderId (Long order_id);
}

