package com.example.smartcityapp.repository.feedbackRepository;

import com.example.smartcityapp.model.entity.clientEntity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
