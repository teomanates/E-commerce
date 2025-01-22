package com.turkcell.ecommerce.dto;

import java.util.List;

public class CreateOrderDto {
    private int orderId;
    private String orderName;
    private int quantity;
    private double unit_price;
    private double total_price;
    private List<OrderProductDto> orderProducts;




}
