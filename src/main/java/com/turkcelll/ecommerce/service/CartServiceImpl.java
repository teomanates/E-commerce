package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.CartItemRequestDto;
import com.turkcelll.ecommerce.dto.CartItemResponseDto;
import com.turkcelll.ecommerce.dto.CartResponseDto;
import com.turkcelll.ecommerce.entity.Cart;
import com.turkcelll.ecommerce.entity.CartItem;
import com.turkcelll.ecommerce.entity.Product;
import com.turkcelll.ecommerce.entity.User;
import com.turkcelll.ecommerce.repository.CartItemRepository;
import com.turkcelll.ecommerce.repository.CartRepository;
import com.turkcelll.ecommerce.repository.ProductRepository;
import com.turkcelll.ecommerce.repository.UserRepository;
import com.turkcelll.ecommerce.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SecurityUtil securityUtil; // JWT'den user email çekmek vs.

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            SecurityUtil securityUtil
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    public CartResponseDto getCartOfCurrentUser() {
        // Şu anki kullanıcıyı bul
        User currentUser = getCurrentUser();

        // Kullanıcının sepetini getir (yoksa oluştur)
        Cart cart = findOrCreateCart(currentUser);
        return toCartResponseDto(cart);
    }

    @Override
    public CartResponseDto addItemToCart(CartItemRequestDto requestDto) {
        User currentUser = getCurrentUser();
        Cart cart = findOrCreateCart(currentUser);

        // Ürünü bul
        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Stok kontrolü
        if (product.getStock() < requestDto.getQuantity()) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        // Cart içerisinde bu ürün zaten var mı diye bak
        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // Varsa miktarı arttır
            CartItem existingItem = existingItemOpt.get();
            int newQty = existingItem.getQuantity() + requestDto.getQuantity();
            if (newQty > product.getStock()) {
                throw new RuntimeException("Cannot exceed product stock!");
            }
            existingItem.setQuantity(newQty);
            existingItem.setSubTotal(existingItem.getQuantity() * product.getPrice());

        } else {
            // Yoksa yeni CartItem oluştur
            CartItem newItem = new CartItem(cart, product, requestDto.getQuantity());
            newItem.setSubTotal(product.getPrice() * requestDto.getQuantity());
            cart.getCartItems().add(newItem);
        }

        // totalPrice güncelle (basit hesaplama)
        recalcCartTotal(cart);

        // Cart kaydet
        cartRepository.save(cart);

        return toCartResponseDto(cart);
    }

    @Override
    public CartResponseDto removeItemFromCart(Long productId) {
        User currentUser = getCurrentUser();
        Cart cart = findOrCreateCart(currentUser);

        // Cart içindeki ilgili item'ı bul
        cart.getCartItems().removeIf(ci -> ci.getProduct().getId().equals(productId));

        recalcCartTotal(cart);
        cartRepository.save(cart);

        return toCartResponseDto(cart);
    }

    @Override
    public CartResponseDto updateItemQuantity(Long productId, int newQuantity) {
        User currentUser = getCurrentUser();
        Cart cart = findOrCreateCart(currentUser);

        // Ürünü bul
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Sepet içinde item var mı
        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        // Stok kontrolü
        if (newQuantity > product.getStock()) {
            throw new RuntimeException("Cannot exceed product stock!");
        }
        cartItem.setQuantity(newQuantity);
        cartItem.setSubTotal(newQuantity * product.getPrice());

        recalcCartTotal(cart);
        cartRepository.save(cart);

        return toCartResponseDto(cart);
    }

    @Override
    public void clearCart() {
        User currentUser = getCurrentUser();
        Cart cart = findOrCreateCart(currentUser);
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    // Yardımcı method: aktif kullanıcıyı bulmak
    private User getCurrentUser() {
        // JWT'den email alıp user'ı getirelim:
        String email = securityUtil.getCurrentUserEmail(); // Kendi projede var
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Yardımcı method: cart bul veya create
    private Cart findOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart(user);
                    return cartRepository.save(newCart);
                });
    }

    // Yardımcı method: cart total hesaplama
    private void recalcCartTotal(Cart cart) {
        double sum = cart.getCartItems().stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();
        cart.setTotalPrice(sum);
    }

    // Sepeti CartResponseDto'ya çeviren basit mapper
    private CartResponseDto toCartResponseDto(Cart cart) {
        CartResponseDto dto = new CartResponseDto();
        dto.setCartId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setItems(
                cart.getCartItems().stream().map(item -> {
                    CartItemResponseDto itemDto = new CartItemResponseDto();
                    itemDto.setProductId(item.getProduct().getId());
                    itemDto.setProductName(item.getProduct().getName());
                    itemDto.setPrice(item.getProduct().getPrice());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setSubTotal(item.getSubTotal());
                    return itemDto;
                }).toList()
        );
        return dto;
    }
}

