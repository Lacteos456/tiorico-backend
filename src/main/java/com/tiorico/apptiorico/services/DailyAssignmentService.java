package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.models.DailyAssignment;

import java.util.List;

public interface DailyAssignmentService
{
    DailyAssignmentDTO createDailyAssignment(DailyAssignmentDTO dto);
    DailyAssignmentDTO getDailyAssignmentById(Integer id);
    List<DailyAssignmentDTO> getAllDailyAssignments();
    DailyAssignmentDTO updateDailyAssignment(Integer id, DailyAssignmentDTO dto);
    void deleteDailyAssignment(Integer id);
}