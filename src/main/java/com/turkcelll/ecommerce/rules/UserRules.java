package com.turkcelll.ecommerce.rules;

import com.turkcelll.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRules {
    private final UserRepository userRepository;

    public UserRules(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void checkIfEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email Already Exists");
        }


    }
}


