package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.CreateOrderDto;
import com.turkcelll.ecommerce.dto.OrderItemDto;
import com.turkcelll.ecommerce.dto.ProductRequestDto;
import com.turkcelll.ecommerce.repository.OrderItemRepository;
import com.turkcelll.ecommerce.repository.OrderRepository;
import com.turkcelll.ecommerce.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    private final Map<Long, CreateOrderDto> orderDatabase = new HashMap<>();
    private final Map<Long, ProductRequestDto> productStockDatabase = new HashMap<>();
    private long orderIdCounter = 1;


    public CreateOrderDto createOrder(CreateOrderDto orderDTO) {
        if (orderDTO.items == null || orderDTO.items.isEmpty()) {
            System.out.println("Sipariş oluşturulmadı: Sepette ürün bulunmamaktadır.");
            return null;
        }

        double totalPrice = 0.0;
        for (OrderItemDto item : orderDTO.items) {
            ProductRequestDto stock = productStockDatabase.get(item.getProduct_id());
            if (stock == null || stock.getStock() < item.getQuantity()) {
                System.out.println("Sipariş oluşturulmadı: " + item.getProduct_id() + " ürünü için stok yetersiz.");
                return null;
            }
            totalPrice += item.getQuantity()* item.getUnit_price();
        }

        for (OrderItemDto item : orderDTO.items) {
            ProductRequestDto stock = productStockDatabase.get(item.getProduct_id());
            stock.setStock(stock.getStock() - item.getQuantity());

        }

        orderDTO.setOrder_id(orderIdCounter++);
        orderDTO.setCreated_at(LocalDateTime.now());
        orderDTO.setUpdated_at(LocalDateTime.now());
        orderDTO.setStatus("Pending");
        orderDTO.setTotal_price(totalPrice);
        orderDatabase.put(orderDTO.getOrder_id(), orderDTO);

        System.out.println("Sipariş başarıyla oluşturuldu.");
        return orderDTO;
    }


    public List<CreateOrderDto> getAllOrders() {
        return new ArrayList<>(orderDatabase.values());
    }


    public CreateOrderDto updateOrderStatus(Long order_id, String status) {
        CreateOrderDto createorderDto = orderDatabase.get(order_id);
        if (createorderDto != null) {
            createorderDto.setStatus(status);
            createorderDto.updated_at = LocalDateTime.now();
        }
        return createorderDto;
    }


    public CreateOrderDto getOrderById(Long order_id) {
        return orderDatabase.get(order_id);
    }


    public void deleteOrder(Long orderId) {
        orderDatabase.remove(orderId);
    }
}