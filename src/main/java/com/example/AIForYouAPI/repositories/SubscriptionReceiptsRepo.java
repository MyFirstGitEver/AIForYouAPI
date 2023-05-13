package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.SubscriptionReceiptsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionReceiptsRepo extends JpaRepository<SubscriptionReceiptsEntity, Integer> {
    List<SubscriptionReceiptsEntity> findByIdUser(int idUser);
}