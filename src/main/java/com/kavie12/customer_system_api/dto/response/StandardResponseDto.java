package com.kavie12.customer_system_api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StandardResponseDto {
    private int code;
    private String message;
    private Object data;
}
