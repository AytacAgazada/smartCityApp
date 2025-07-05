package com.example.smartcityapp.model.dto.clientDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    @NotBlank(message = "Name can't be blank")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email can't be blank")
    private String email;

    @NotBlank(message = "Message can't be blank")
    private String message;
}
