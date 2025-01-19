package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.models.DailyAssignment;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DailyAssignmentService
{
    DailyAssignmentDTO createDailyAssignment(DailyAssignmentDTO dto);
    List<DailyAssignmentDTO> getAllAssignments();
    List<DailyAssignmentDTO> getAssignmentsByDate(LocalDate date);
    DailyAssignmentDTO updateDailyAssignment(Integer id, DailyAssignmentDTO dto);
    void deleteDailyAssignment(Integer id);
    public DailyAssignmentDTO getDailyAssignmentById(Integer id);
}