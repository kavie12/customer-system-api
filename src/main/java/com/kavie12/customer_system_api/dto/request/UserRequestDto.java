package com.kavie12.customer_system_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "Username is required.")
    @Size(max = 40, message = "Username is limited to 40 characters")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;
}
