package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.entity.Cart;
import com.turkcelll.ecommerce.entity.CartItem;
import com.turkcelll.ecommerce.entity.Product; //procuct şuan yok bu yüzden hata verir
import com.turkcelll.ecommerce.repository.CartRepository;
import com.turkcelll.ecommerce.repository.ProductRepository; //productRepository şuan yok bu yüzden hata verir
import com.turkcelll.ecommerce.service.CartService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getCartItems().add(newItem);
        }

        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    public void updateProductQuantity(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }
}
