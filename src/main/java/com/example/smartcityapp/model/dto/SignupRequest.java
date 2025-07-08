package com.example.smartcityapp.model.dto;

import com.example.smartcityapp.model.enumeration.Roles;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{8,}$",
            message = "Password must be at least 8 characters long and contain an uppercase letter, a lowercase letter, a digit, and a special character (!@#$%^&*()_+)."
    )
    private String password;


    @NotNull(message = "Role must be provided")
    private Roles role;

}
