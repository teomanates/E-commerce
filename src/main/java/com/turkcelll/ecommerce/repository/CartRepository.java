package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.Cart;
import com.turkcelll.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Bir kullanıcıya ait cart var mı?
    Optional<Cart> findByUser(User user);
}
