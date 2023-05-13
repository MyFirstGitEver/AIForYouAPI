package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.AIReceiptsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIReceiptsRepo extends JpaRepository<AIReceiptsEntity, Integer> {
    AIReceiptsEntity findByIdNguoiMuaAndIdModel(int idNguoiMua, int idModel);
    List<AIReceiptsEntity> findByIdNguoiMua(int idNguoiMua);
}