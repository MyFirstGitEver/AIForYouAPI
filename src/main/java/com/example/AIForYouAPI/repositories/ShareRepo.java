package com.example.AIForYouAPI.repositories;

import com.example.AIForYouAPI.entities.ShareEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projections.SenderWithMaxDate;

import java.util.Date;
import java.util.List;

public interface ShareRepo extends JpaRepository<ShareEntity, Integer> {

    @Query("Select new projections.SenderWithMaxDate(idNguoiGui, max(sentDate) as d) from ShareEntity " +
            "Where idNguoiNhan = :userId group by idNguoiGui order by d desc")
    List<SenderWithMaxDate> findAllLastShared(int userId);

    List<ShareEntity> findByIdNguoiGuiAndSentDate(int idNguoiGui, Date sentDate, Pageable pageable);

    List<ShareEntity> findByIdNguoiNhanOrderBySentDateDesc(int idNguoiNhan);
}