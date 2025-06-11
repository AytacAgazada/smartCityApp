package com.example.smartcityapp.model.dto;

import com.example.smartcityapp.model.enumeration.Status;
import lombok.Data;

@Data
public class SmartCityRequestDto {

    private String description;

    private Status status;

    private String title;

}
