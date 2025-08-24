package com.kavie12.customer_system_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordDto {

    @NotBlank(message = "Old password is required.")
    String oldPassword;

    @NotBlank(message = "New password is required.")
    String newPassword;
}
