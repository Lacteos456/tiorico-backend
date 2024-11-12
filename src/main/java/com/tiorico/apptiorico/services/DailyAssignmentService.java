package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.models.DailyAssignment;

import java.util.List;
import java.util.Map;

public interface DailyAssignmentService
{
    DailyAssignmentDTO createDailyAssignment(DailyAssignmentDTO dto);
    DailyAssignmentDTO getDailyAssignmentById(Integer id);
    List<DailyAssignmentDTO> getAllDailyAssignments();
    DailyAssignmentDTO updateDailyAssignment(Integer id, DailyAssignmentDTO dto);
    void deleteDailyAssignment(Integer id);
    Map<String, Integer> getProductStockAndUnitsPerBox(Integer productId);
}