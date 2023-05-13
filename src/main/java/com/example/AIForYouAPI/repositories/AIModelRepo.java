package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.AIModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AIModelRepo extends JpaRepository<AIModelEntity, Integer> {
    AIModelEntity findByTenModel(String tenModel);
    @Query("SELECT m from AIModelEntity m LEFT JOIN AIReceiptsEntity r ON (m.id = r.idModel AND r.idNguoiMua = :userId)" +
            " Where r.idModel = NULL")
    List<AIModelEntity> findAllUnpurchasedModels(int userId);
}