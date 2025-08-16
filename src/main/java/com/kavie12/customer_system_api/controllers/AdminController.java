package com.kavie12.customer_system_api.controllers;

import com.kavie12.customer_system_api.dto.request.UserRequestDto;
import com.kavie12.customer_system_api.dto.response.StandardResponseDto;
import com.kavie12.customer_system_api.services.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<StandardResponseDto> register(@Valid @RequestBody UserRequestDto dto) {
        adminService.register(dto);
        return new ResponseEntity<>(
                new StandardResponseDto(201, "Admin registered.", null),
                HttpStatus.CREATED
        );
    }

}
