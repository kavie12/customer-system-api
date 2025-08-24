package com.kavie12.customer_system_api.services;

import com.kavie12.customer_system_api.dto.request.UserRequestDto;
import com.kavie12.customer_system_api.entities.User;
import com.kavie12.customer_system_api.exception.UserAlreadyExistsException;
import com.kavie12.customer_system_api.repositories.UserRepository;
import com.kavie12.customer_system_api.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRequestDto dto) {
        User user = toUser(dto);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException("An admin with the same username is already registered.");
        }
    }

    private User toUser(UserRequestDto dto) {
        return dto == null ? null :
                User.builder()
                        .username(dto.getUsername())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .role(UserRole.ADMIN)
                        .build();
    }

}
