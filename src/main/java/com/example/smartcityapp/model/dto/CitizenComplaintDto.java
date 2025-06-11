package com.example.smartcityapp.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CitizenComplaintDto {

    @NotBlank(message = "Full name can't be empty")
    @Size(min = 2, max = 50, message = "Full name length should be between 2 and 50 characters")
    private String fullName;

    @Email(message = "Email is not valid")
    @NotBlank(message = "Email can't be blank")
    private String email;

    @Pattern(regexp = "^\\+994\\d{9}$", message = "Phone number must match +994XXXXXXXXX format")
    private String phoneNumber;

    @NotBlank(message = "Complaint message can't be empty")
    @Size(min = 15, max = 500, message = "Complaint message length should be between 15 and 500 characters")
    private String complaintMessage;

    @NotBlank(message = "Status can't be blank")
    @Pattern(regexp = "OPEN|IN_PROGRESS|RESOLVED", message = "Status must be one of: OPEN, IN_PROGRESS, RESOLVED")
    private String status;

    @NotNull(message = "Priority is required")
    @Min(value = 1, message = "Priority must be between 1 and 3")
    @Max(value = 3, message = "Priority must be between 1 and 3")
    private Integer priority;
}
