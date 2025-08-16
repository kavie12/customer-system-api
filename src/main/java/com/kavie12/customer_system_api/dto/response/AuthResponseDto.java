package com.kavie12.customer_system_api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
}
