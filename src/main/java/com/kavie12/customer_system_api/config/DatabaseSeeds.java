package com.kavie12.customer_system_api.config;

import com.kavie12.customer_system_api.entities.User;
import com.kavie12.customer_system_api.enums.UserRole;
import com.kavie12.customer_system_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeds implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = User.builder()
                    .username("admin-1")
                    .password(passwordEncoder.encode("1234"))
                    .role(UserRole.ADMIN)
                    .build();
            userRepository.save(user);
            System.out.println("Default admin account created: {username: admin-1, password: 1234}");
        }
    }
}
