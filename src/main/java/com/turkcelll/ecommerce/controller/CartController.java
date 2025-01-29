package com.turkcelll.ecommerce.controller;

import com.turkcelll.ecommerce.dto.CartItemRequestDto;
import com.turkcelll.ecommerce.dto.CartResponseDto;
import com.turkcelll.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Sepeti görüntüleme
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart() {
        return ResponseEntity.ok(cartService.getCartOfCurrentUser());
    }

    // Sepete ürün ekleme
    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addItem(@RequestBody CartItemRequestDto dto) {
        return ResponseEntity.ok(cartService.addItemToCart(dto));
    }

    // Sepetten ürün silme
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartResponseDto> removeItem(@PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(productId));
    }

    // Sepetteki miktarı güncelleme
    @PutMapping("/update/{productId}")
    public ResponseEntity<CartResponseDto> updateQuantity(
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.updateItemQuantity(productId, quantity));
    }

    // Tüm sepeti temizleme (opsiyonel)
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }
}
