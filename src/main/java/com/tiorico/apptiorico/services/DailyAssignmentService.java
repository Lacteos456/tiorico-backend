package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.DailyAssignment;

import java.util.List;

public interface DailyAssignmentService
{
    List<DailyAssignment> findAll();
    DailyAssignment save(DailyAssignment assignment);
    DailyAssignment findById(Integer id);
    void delete(Integer id);
    public DailyAssignment findByUserIdAndProductId(Integer userId, Integer productId);
}