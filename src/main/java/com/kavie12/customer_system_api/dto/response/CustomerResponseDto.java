package com.kavie12.customer_system_api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDto {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
