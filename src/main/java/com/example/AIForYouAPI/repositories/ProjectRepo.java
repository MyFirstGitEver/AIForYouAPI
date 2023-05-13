package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<ProjectEntity, Integer> {
}