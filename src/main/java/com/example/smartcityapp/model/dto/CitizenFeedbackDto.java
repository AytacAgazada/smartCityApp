package com.example.smartcityapp.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CitizenFeedbackDto {

    @NotBlank(message = "name can't be empty")
    @Size(min=2,max=30,message = "name length should be min 2 max 30")
    private String name;

    @Email(message = "email is not valid")
    @NotBlank(message = "email can't be blank")
    private String email;

    @Pattern(regexp = "^\\+994\\d{9}$", message = "Phone number must match +994XXXXXXXXX format")
    private String phoneNumber;

    @NotBlank(message = "can't be empty")
    @Size(min=10,max=300,message = "message length snot valid")
    private String message;

    @Min(value = 1,message = "rating can be between 1-5")
    @Max(value = 5,message = "rating can be between 1-5")
    private int reting;
}
