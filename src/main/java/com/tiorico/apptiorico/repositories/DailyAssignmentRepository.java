package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.DailyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyAssignmentRepository extends JpaRepository<DailyAssignment, Integer>
{
    List<DailyAssignment> findByAssignmentDate(LocalDate date);
    long countByUserId(long userId);

    @Modifying
    @Transactional
    @Query("UPDATE DailyAssignment da SET da.sellerBoxes = :sellerBoxes, da.sellerUnits = :sellerUnits WHERE da.id = :id")
    void updateSellerFields(@Param("id") Integer id, @Param("sellerBoxes") Integer sellerBoxes, @Param("sellerUnits") Integer sellerUnits);
}