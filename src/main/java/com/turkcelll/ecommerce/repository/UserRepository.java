package com.turkcelll.ecommerce.repository;

import com.turkcelll.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByEmail (String email); // dto mu kullanılmalı ?
   boolean existByEmail (String email);
}
