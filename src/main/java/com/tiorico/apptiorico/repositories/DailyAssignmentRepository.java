package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.DailyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyAssignmentRepository extends JpaRepository<DailyAssignment, Integer>
{

}