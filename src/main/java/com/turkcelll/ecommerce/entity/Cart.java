package com.turkcelll.ecommerce.entity;

import com.turkcelll.ecommerce.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //her sepetin bir id si olacak ve bu id ile sepete erişim sağlanacak

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //her sepetin bir kullanıcısı olacak ve bu kullanıcıya erişim sağlanacak

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.turkcelll.ecommerce.entity.CartItem> cartItems; //her sepetin birden fazla ürünü olacak ve bu ürünlere erişim sağlanacak

    private Double totalPrice = 0.0; //her sepetin bir toplam fiyatı olacak ve bu fiyata erişim sağlanacak

    public void calculateTotalPrice() { //her sepetin toplam fiyatını hesaplayacak
        this.totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()) //her ürünün fiyatı ile miktarını çarparak toplam fiyatı hesaplar
                .sum();
    }
}
