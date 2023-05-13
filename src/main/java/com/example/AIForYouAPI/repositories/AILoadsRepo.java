package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.AILoadsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AILoadsRepo extends JpaRepository<AILoadsEntity, Integer> {
    AILoadsEntity findByIdProject(int idProject);
}
