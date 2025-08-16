package com.kavie12.customer_system_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto {

    @NotBlank(message = "First name is required.")
    @Size(max = 40, message = "First name is limited to 40 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 40, message = "Last name is limited to 40 characters.")
    private String lastName;

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number is limited to 20 digits")
    private String phoneNumber;

    @NotBlank(message = "Username is required.")
    @Size(max = 40, message = "Username is limited to 40 characters")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;
}
