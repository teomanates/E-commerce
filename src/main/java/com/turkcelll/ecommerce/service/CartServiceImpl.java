package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.entity.Cart;
import com.turkcelll.ecommerce.entity.CartItem;
import com.turkcelll.ecommerce.entity.Product;
import com.turkcelll.ecommerce.repository.CartRepository;
import com.turkcelll.ecommerce.repository.ProductRepository;
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
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        Cart cart = cartRepository.findByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("The requested product does not exist."));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("The requested quantity exceeds the available stock.");
        }

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
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

        product.setStockQuantity(product.getStockQuantity() - quantity); // Stok azaltılıyor
        productRepository.save(product);

        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId);

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            // Ürünün stoğunu geri artır
            Product product = cartItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + cartItem.getQuantity());
            productRepository.save(product);

            // Ürünü sepetten kaldır
            cart.getCartItems().remove(cartItem);
        } else {
            throw new RuntimeException("The product is not in the cart.");
        }

        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    @Override
    public void updateProductQuantity(Long userId, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }

        Cart cart = cartRepository.findByUserId(userId);
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            Product product = cartItem.getProduct();

            // Stoğu güncelle
            int difference = quantity - cartItem.getQuantity();
            if (product.getStockQuantity() < difference) {
                throw new RuntimeException("The requested quantity exceeds the available stock.");
            }

            product.setStockQuantity(product.getStockQuantity() - difference);
            productRepository.save(product);

            // Miktarı güncelle
            cartItem.setQuantity(quantity);
        } else {
            throw new RuntimeException("The product is not in the cart.");
        }

        cart.calculateTotalPrice();
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);

        // Stokları geri ekle
        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            productRepository.save(product);
        }

        // Sepeti temizle
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}
