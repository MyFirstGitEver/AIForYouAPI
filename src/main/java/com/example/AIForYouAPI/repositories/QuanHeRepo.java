package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.QuanHeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuanHeRepo extends JpaRepository<QuanHeEntity, Integer> {
    @Query("SELECT q from QuanHeEntity q Where (idFirst = :idA or idSecond = :idB) AND accepted = 1")
    List<QuanHeEntity> findByIdFirstOrIdSecond(int idA, int idB);
}
