package com.turkcelll.ecommerce.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@Component
public class SecurityUtil {

    private final PasswordEncoder passwordEncoder;

    public SecurityUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Eğer kimlik doğrulanmamışsa ya da authentication null ise hata fırlatalım
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found!");
        }
        // Bizim JwtRequestFilter içinde username = email atandığı için:
        return authentication.getName();
    }
}
