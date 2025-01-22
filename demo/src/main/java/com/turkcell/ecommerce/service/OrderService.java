package com.turkcell.ecommerce.service;

// OrdersService.java
import com.turkcell.ecommerce.dto.CreateOrderDto;
import com.turkcell.ecommerce.entity.Order;

import java.util.List;

public interface OrderService {

    // Sipariş oluşturma
    Order createOrder(CreateOrderDto createOrderDto) throws Exception;

    // Sipariş listeleme
    List<Order> getAllOrders();

    // Sipariş durumu güncelleme
    Order updateOrderStatus(Long orderId, String status) throws Exception;
}

//controller-service-repository