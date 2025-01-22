package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.entity.Cart;

public interface CartService {
    Cart getCartByUserId(Long userId);
    void addProductToCart(Long userId, Long productId, int quantity);
    void removeProductFromCart(Long userId, Long productId);
    void updateProductQuantity(Long userId, Long productId, int quantity);
}
