package com.turkcelll.ecommerce.entity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity // orm bu classı tanıyor.
@NoArgsConstructor
@Table(name = "order")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "order_id" , unique = true)
    private Long id;

    @Column ( name = "order_number", nullable = false)
    private int orderNumber;

    @Column (name = "total_price", nullable = false)
    private double totalPrice;

    @Column ( name = "created_at", nullable = false)
    private LocalDateTime creataedAt;

    @Column ( name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreataedAt() {
        return creataedAt;
    }

    public void setCreataedAt(LocalDateTime creataedAt) {
        this.creataedAt = creataedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @ManyToOne()
    @JoinColumn( name = "user_id", nullable = true)
    private User user;

    @OneToMany()
    @JoinColumn( name= "order_id", nullable = true)
    private List<OrderItem> orderItems;

    @OneToOne()
    @JoinColumn( name= "order_id", nullable = true)
    private OrderStatus orderStatus;


}

