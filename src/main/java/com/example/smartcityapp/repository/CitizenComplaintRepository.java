package com.example.smartcityapp.repository;

import com.example.smartcityapp.model.entity.CitizenComplaintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenComplaintRepository extends JpaRepository<CitizenComplaintEntity,Long> {
}
