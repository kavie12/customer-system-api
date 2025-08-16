package com.kavie12.customer_system_api.services;

import com.kavie12.customer_system_api.config.JwtService;
import com.kavie12.customer_system_api.dto.request.AuthRequestDto;
import com.kavie12.customer_system_api.dto.response.AuthResponseDto;
import com.kavie12.customer_system_api.exception.NoSuchUserExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthResponseDto authenticate(AuthRequestDto dto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return AuthResponseDto.builder()
                    .accessToken(jwtService.generateAccessToken(dto.getUsername()))
                    .refreshToken(jwtService.generateRefreshToken(dto.getUsername()))
                    .build();
        }

        return null;
    }

    public AuthResponseDto refresh(String authHeader) throws IOException {
        String token = null;
        String username = null;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid token.");
        }

        token = authHeader.substring(7);

        if (jwtService.isTokenExpired(token)) {
            throw new BadRequestException("Token is expired. Please login again.");
        }

        username = jwtService.extractUsername(token);

        if (username == null) {
            throw new NoSuchUserExistsException("Invalid user.");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new BadRequestException("Invalid token.");
        }

        return AuthResponseDto.builder()
                .accessToken(jwtService.generateAccessToken(username))
                .refreshToken(token)
                .build();
    }
}
