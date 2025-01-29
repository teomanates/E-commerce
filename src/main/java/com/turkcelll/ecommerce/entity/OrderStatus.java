package com.turkcelll.ecommerce.entity;

import com.turkcelll.ecommerce.service.OrderService;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table( name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name= "order_status_id", unique = true)
    private Long id;

    @Column(length = 255, nullable = false)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @OneToOne()
    @JoinColumn(name= "order_id", nullable = true) //Order ile ilişki
    private Order order;

}




