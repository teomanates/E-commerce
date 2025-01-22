package com.turkcell.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column ( name = "order_item_id")
    private int orderItemId;

    @Column ( name = "quantity")
    private int quantity ;

    @ManyToOne()
    @JoinColumn ( name = "order_id")
    private Order order;

    @OneToOne()
    @JoinColumn ( name = "order_item_id")
    private OrderItem orderItem;

}
.

