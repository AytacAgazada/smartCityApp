package com.example.smartcityapp.controller.clientController;

import com.example.smartcityapp.model.dto.clientDto.FeedbackDto;
import com.example.smartcityapp.model.entity.clientEntity.Feedback;
import com.example.smartcityapp.service.clientService.FeedbackService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback/client")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> createFeedback(@Valid @RequestBody FeedbackDto feedbackDto) {
        System.out.println("SmartCityDemo: CREATE - BFF-dən feedback qəbul edildi: " + feedbackDto.getEmail());

        Feedback feedbackEntity = new Feedback();
        BeanUtils.copyProperties(feedbackDto, feedbackEntity); // DTO-dan Entity-ə çevirmə

        Feedback createdFeedbackEntity = feedbackService.createFeedback(feedbackEntity);

        FeedbackDto createdFeedbackDto = new FeedbackDto();
        BeanUtils.copyProperties(createdFeedbackEntity, createdFeedbackDto);

        System.out.println("SmartCityDemo: CREATE - Feedback ID: " + createdFeedbackEntity.getId() + " ilə emal edildi.");
        return new ResponseEntity<>(createdFeedbackDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long id) {
        System.out.println("SmartCityDemo: READ - ID " + id + " ilə feedback tələb edildi.");
        return feedbackService.getFeedbackById(id)
                .map(feedbackEntity -> {
                    FeedbackDto feedbackDto = new FeedbackDto();
                    BeanUtils.copyProperties(feedbackEntity, feedbackDto);
                    return new ResponseEntity<>(feedbackDto, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getAllFeedback() {
        System.out.println("SmartCityDemo: READ - Bütün feedback-lər tələb edildi.");
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        List<FeedbackDto> feedbackDtos = feedbacks.stream()
                .map(feedbackEntity -> {
                    FeedbackDto dto = new FeedbackDto();
                    BeanUtils.copyProperties(feedbackEntity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(feedbackDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDto> updateFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackDto feedbackDto) {
        System.out.println("SmartCityDemo: UPDATE - ID " + id + " feedback üçün yenilənmə sorğusu alındı.");
        try {
            Feedback feedbackEntity = new Feedback();
            BeanUtils.copyProperties(feedbackDto, feedbackEntity);

            Feedback updatedFeedbackEntity = feedbackService.updateFeedback(id, feedbackEntity);

            FeedbackDto updatedFeedbackDto = new FeedbackDto();
            BeanUtils.copyProperties(updatedFeedbackEntity, updatedFeedbackDto);

            System.out.println("SmartCityDemo: UPDATE - Feedback ID " + id + " uğurla yeniləndi.");
            return new ResponseEntity<>(updatedFeedbackDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.err.println("SmartCityDemo: UPDATE - Xəta: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        System.out.println("SmartCityDemo: DELETE - ID " + id + " feedback üçün silinmə sorğusu alındı.");
        try {
            feedbackService.deleteFeedback(id);
            System.out.println("SmartCityDemo: DELETE - Feedback ID " + id + " uğurla silindi.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            System.err.println("SmartCityDemo: DELETE - Xəta: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}