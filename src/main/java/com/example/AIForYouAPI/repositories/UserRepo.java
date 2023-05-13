package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    UserEntity findByTenDn(String tenDn);
}
