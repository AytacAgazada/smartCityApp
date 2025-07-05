package com.example.smartcityapp.service.clientService;

import com.example.smartcityapp.client.NotificationClient;


import com.example.smartcityapp.model.dto.clientDto.NotificationRequest;
import com.example.smartcityapp.model.entity.clientEntity.Feedback;
import com.example.smartcityapp.repository.feedbackRepository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final NotificationClient notificationClient;

    public FeedbackService(FeedbackRepository feedbackRepository, NotificationClient notificationClient) {
        this.feedbackRepository = feedbackRepository;
        this.notificationClient = notificationClient;
    }

    public Feedback createFeedback(Feedback feedbackEntity) {
        if (feedbackEntity == null) {
            throw new IllegalArgumentException("Feedback object cannot be null.");
        }
        if (feedbackEntity.getName() == null || feedbackEntity.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name field cannot be empty.");
        }
        if (feedbackEntity.getEmail() == null || feedbackEntity.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be empty.");
        }

        feedbackEntity.setSubmissionTime(LocalDateTime.now());

        Feedback savedFeedback = feedbackRepository.save(feedbackEntity);

        System.out.println("SmartCityDemo: Feedback with ID: " + savedFeedback.getId() + " saved to DB.");

        try {
            NotificationRequest notificationRequest = new NotificationRequest(
                    savedFeedback.getName(),
                    savedFeedback.getEmail(),
                    savedFeedback.getMessage()
            );
            notificationClient.sendNotification(notificationRequest);
            System.out.println("SmartCityDemo: Notification request sent for feedback ID: " + savedFeedback.getId() + ".");
        } catch (Exception e) {
            System.err.println("SmartCityDemo: Failed to send notification for feedback ID " + savedFeedback.getId() + ": " + e.getMessage());
        }

        return savedFeedback;
    }

    public Optional<Feedback> getFeedbackById(Long id) {
        System.out.println("SmartCityDemo: Feedback requested with ID " + id + ".");
        return feedbackRepository.findById(id);
    }

    public List<Feedback> getAllFeedback() {
        System.out.println("SmartCityDemo: All feedbacks requested.");
        return feedbackRepository.findAll();
    }

    public Feedback updateFeedback(Long id, Feedback updatedFeedbackEntity) {
        if (updatedFeedbackEntity == null) {
            throw new IllegalArgumentException("Updated Feedback object cannot be null.");
        }

        // Rəyi ID ilə tapıb yeniləyirik
        return feedbackRepository.findById(id).map(existingFeedback -> {
            existingFeedback.setName(updatedFeedbackEntity.getName());
            existingFeedback.setEmail(updatedFeedbackEntity.getEmail());
            existingFeedback.setPhoneNumber(updatedFeedbackEntity.getPhoneNumber());
            existingFeedback.setMessage(updatedFeedbackEntity.getMessage());
            existingFeedback.setRating(updatedFeedbackEntity.getRating());

            Feedback savedFeedback = feedbackRepository.save(existingFeedback);
            System.out.println("SmartCityDemo: Feedback ID " + savedFeedback.getId() + " updated.");
            return savedFeedback;
        }).orElseThrow(() -> new IllegalArgumentException("Feedback with ID " + id + " not found."));
    }

    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new IllegalArgumentException("Feedback with ID " + id + " to be deleted not found.");
        }
        feedbackRepository.deleteById(id);
        System.out.println("SmartCityDemo: Feedback ID " + id + " deleted.");
    }
}