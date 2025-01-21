package com.turkcelll.ecommerce.controller;

import com.turkcelll.ecommerce.entity.Cart;
import com.turkcelll.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long userId,
                                                 @RequestParam Long productId,
                                                 @RequestParam int quantity) {
        cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/remove")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long userId,
                                                      @RequestParam Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }
}

