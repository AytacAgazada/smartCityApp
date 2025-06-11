package com.example.smartcityapp.repository;

import com.example.smartcityapp.model.entity.CitizenFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenFeedbackRepository extends JpaRepository<CitizenFeedback,Long> {
}
