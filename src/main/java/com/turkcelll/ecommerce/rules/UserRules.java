package com.turkcelll.ecommerce.rules;

import com.turkcelll.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRules {
    private final UserRepository userRepository;

    public UserRules(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateEmail(String email) {
        if(!email.matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")){
            throw new RuntimeException("Invalid email format");
        }
    }

    public void checkEmailExists(String email) {
        if(userRepository.findByEmail(email){
            throw new RuntimeException("Email already exists");
        }
    }

    public void validatePassword(String password) {
        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")){
            throw new RuntimeException("Password must be at least 8 characters, include a number, an uppercase and a lowercase letter");
        }
    }
}
