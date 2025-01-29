package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.CreateOrderDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrderService {
    CreateOrderDto addOrder(CreateOrderDto createOrderDto);
        CreateOrderDto createOrder(CreateOrderDto createOrderDto);
        List<CreateOrderDto> getAllOrders();
        CreateOrderDto updateOrderStatus(Long order_id, String status);
        CreateOrderDto getOrderById(Long order_id);
        void deleteOrder(Long order_id);
    }


