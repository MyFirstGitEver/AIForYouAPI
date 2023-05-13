package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepo extends JpaRepository<SubscriptionEntity, Integer> {
    @Query("SELECT s from SubscriptionEntity s LEFT JOIN SubscriptionReceiptsEntity r ON " +
            "(s.id = r.idSubs AND r.idUser = :userId) Where r.idSubs = NULL")
    List<SubscriptionEntity> findAllUnpurchasedSubs(int userId);
}