package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.ModelUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelUsageRepo extends JpaRepository<ModelUsageEntity, Integer> {
    long countByUserId(int userId);
}
