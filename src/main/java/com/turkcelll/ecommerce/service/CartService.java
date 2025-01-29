package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.CartItemRequestDto;
import com.turkcelll.ecommerce.dto.CartResponseDto;

public interface CartService {

    CartResponseDto getCartOfCurrentUser();
    CartResponseDto addItemToCart(CartItemRequestDto requestDto);
    CartResponseDto removeItemFromCart(Long productId);
    CartResponseDto updateItemQuantity(Long productId, int newQuantity);
    void clearCart(); // silebilirim ??
}
