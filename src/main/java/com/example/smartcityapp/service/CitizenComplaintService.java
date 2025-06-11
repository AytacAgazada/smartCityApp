package com.example.smartcityapp.service;

import com.example.smartcityapp.exception.ResourceNoFoundException;
import com.example.smartcityapp.model.dto.CitizenComplaintDto;
import com.example.smartcityapp.model.entity.CitizenComplaintEntity;
import com.example.smartcityapp.repository.CitizenComplaintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenComplaintService {

    private final CitizenComplaintRepository citizenComplaintRepository;

    public CitizenComplaintService(CitizenComplaintRepository citizenComplaintRepository) {
        this.citizenComplaintRepository = citizenComplaintRepository;
    }

    public CitizenComplaintEntity createComplaint(CitizenComplaintDto citizenComplaintDto) {
        CitizenComplaintEntity citizenComplaintEntity = new CitizenComplaintEntity();
        citizenComplaintEntity.setComplaintMessage(citizenComplaintDto.getComplaintMessage());
        citizenComplaintEntity.setEmail(citizenComplaintDto.getEmail());
        citizenComplaintEntity.setPhoneNumber(citizenComplaintDto.getPhoneNumber());
        citizenComplaintEntity.setPriority(citizenComplaintDto.getPriority());

        return  citizenComplaintRepository.save(citizenComplaintEntity);
    }
    public List<CitizenComplaintEntity> getAllComplaints() {
        return citizenComplaintRepository.findAll();
    }

    public CitizenComplaintEntity getComplaintById(Long id) {
        return citizenComplaintRepository.findById(id).orElseThrow(() ->
                new ResourceNoFoundException("Complaint not found with ID: " + id));
    }


    public CitizenComplaintEntity updateComplaint(Long id, String status, Integer priority) {
        CitizenComplaintEntity citizenComplaintEntity = getComplaintById(id);
        citizenComplaintEntity.setComplaintMessage(status);
        citizenComplaintEntity.setPriority(priority);
        return  citizenComplaintRepository.save(citizenComplaintEntity);
    }

    public void deleteComplaintById(Long id) {
        CitizenComplaintEntity citizenComplaintEntity = getComplaintById(id);
        citizenComplaintRepository.delete(citizenComplaintEntity);
    }
}
