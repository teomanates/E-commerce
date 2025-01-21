package com.turkcelll.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem { //her sepetin bir ürünü olacak ve bu ürüne erişim sağlanacak
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //her ürünün bir id si olacak ve bu id ile ürüne erişim sağlanacak

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private com.turkcelll.ecommerce.entity.Cart cart; //her ürünün bir sepeti olacak ve bu sepete erişim sağlanacak

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) //her ürünün bir ürünü olacak ve bu ürüne erişim sağlanacak
    private Product product; //her ürünün bir ürünü olacak ve bu ürüne erişim sağlanacak bu ürünün bir id si olacak ve bu id ile ürüne erişim sağlanacak

    private int quantity; //her ürünün bir miktarı olacak ve bu miktar ile ürüne erişim sağlanacak
}
