package com.turkcell.ecommerce.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Entity // orm bu classı tanıyor.
@Table(name = "order") // orm bu classı db deki order tablosu ile eşleştirir.
@Getter
@Setter
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "order_id") // eğer isimler aynıysa bu anotasyon opsiyoneldir.
    private int orderId;

    @Column ( name = "order_number")
    private int orderNumber;

    @Column (name = "total_price")
    private double totalPrice;

    @Column ( name = "created_at")
    private Date creataedAt;

    @Column ( name = "update_at")
    private Date updateAt;

    public Order() {
    }
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public Date getCreataedAt() {
        return creataedAt;
    }

    public void setCreataedAt(Date creataedAt) {
        this.creataedAt = creataedAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
    @ManyToOne()
    @JoinColumn( name = "user_id")
    private User user;

    @OneToMany()
    @JoinColumn( name= "order_id")
    private List<OrderItem> orderItems;

    @OneToOne()
    @JoinColumn( name= "order_id")
    private OrderStatus orderStatus;
}
