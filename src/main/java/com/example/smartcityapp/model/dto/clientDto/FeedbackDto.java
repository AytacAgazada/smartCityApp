package com.example.smartcityapp.model.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    private Long id;

    @NotBlank(message = "Name field cannot be empty.")
    private String name;

    @NotBlank(message = "Email field cannot be empty.")
    @Email(message = "Please provide a valid email format.")
    private String email;

    private String phoneNumber;

    @NotBlank(message = "Message field cannot be empty.")
    private String message;

    @Min(value = 1, message = "Rating cannot be less than 1.")
    @Max(value = 5, message = "Rating cannot be greater than 5.")
    private int rating;

    private LocalDateTime submissionTime;
}
