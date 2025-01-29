package com.turkcelll.ecommerce.controller;

import com.turkcelll.ecommerce.dto.CreateOrderDto;
import com.turkcelll.ecommerce.entity.Order;
import com.turkcelll.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//Dışarıdan istek gelen yer.

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {this.orderService = orderService;}

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDto createOrderDto) {
        try {
            CreateOrderDto order = orderService.createOrder(createOrderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<CreateOrderDto>> listOrders() {
        List<CreateOrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update-status/{order_id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long order_id, @RequestParam String status) {
        try {
            CreateOrderDto updatedOrder = orderService.updateOrderStatus(order_id, status);

            if (updatedOrder == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sipariş bulunamadı: " + order_id);
            }

            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Geçersiz sipariş durumu: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sipariş durumu güncellenirken hata oluştu.");
        }
    }

}