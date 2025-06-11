package com.example.smartcityapp.service;

import com.example.smartcityapp.exception.ResourceNoFoundException;
import com.example.smartcityapp.model.dto.CitizenFeedbackDto;
import com.example.smartcityapp.model.entity.CitizenFeedback;
import com.example.smartcityapp.repository.CitizenFeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class CitizenFeedbackService {

    private final CitizenFeedbackRepository citizenFeedbackRepository;

    public CitizenFeedbackService(CitizenFeedbackRepository citizenFeedbackRepository) {
        this.citizenFeedbackRepository = citizenFeedbackRepository;
    }

    public CitizenFeedback create(CitizenFeedbackDto citizenFeedbackDto) {
        CitizenFeedback citizenFeedback = new CitizenFeedback();
        citizenFeedback.setName(citizenFeedbackDto.getName());
        citizenFeedback.setReting(citizenFeedbackDto.getReting());
        citizenFeedback.setMessage(citizenFeedbackDto.getMessage());
        citizenFeedback.setEmail(citizenFeedbackDto.getEmail());
        citizenFeedback.setPhoneNumber(citizenFeedbackDto.getPhoneNumber());

        return citizenFeedbackRepository.save(citizenFeedback);
    }

    public CitizenFeedback getById(Long id) {
        return citizenFeedbackRepository.findById(id)
                .orElseThrow(() -> new ResourceNoFoundException("citizen feedback not foundwith id " + id));
    }
}
