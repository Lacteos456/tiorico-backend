package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.DailyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyAssignmentRepository extends JpaRepository<DailyAssignment, Integer>
{
    List<DailyAssignment> findByAssignmentDate(LocalDate date);
    long countByUserId(long userId);
}