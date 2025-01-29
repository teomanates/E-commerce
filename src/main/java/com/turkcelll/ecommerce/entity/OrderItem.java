package com.turkcelll.ecommerce.entity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table (name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column( name = "order_item_id", unique = true)
    private Long id;

    @Column ( name = "quantity" , nullable = false)
    private int quantity ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    @ManyToOne()
    @JoinColumn ( name = "order_id", nullable = true) //Order ile ilişki
    private Order order;

    @OneToOne()
    @JoinColumn ( name = "order_item_id", nullable = true) //Product ile ilişki
    private OrderItem orderItem;

}


