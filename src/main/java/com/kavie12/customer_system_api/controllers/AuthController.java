package com.kavie12.customer_system_api.controllers;

import com.kavie12.customer_system_api.dto.request.AuthRequestDto;
import com.kavie12.customer_system_api.dto.response.StandardResponseDto;
import com.kavie12.customer_system_api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<StandardResponseDto> login(@RequestBody AuthRequestDto dto) {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Login Success.", authService.authenticate(dto)),
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<StandardResponseDto> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws IOException {
        return new ResponseEntity<>(
                new StandardResponseDto(200, "Login Success.", authService.refresh(authHeader)),
                HttpStatus.OK
        );
    }

}
