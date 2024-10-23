package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.repositories.DailyAssignmentRepository;
import com.tiorico.apptiorico.services.DailyAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyAssignmentServiceImpl implements DailyAssignmentService
{
    @Autowired
    private DailyAssignmentRepository dailyAssignmentRepository;

    @Override
    public List<DailyAssignment> findAll() {
        return dailyAssignmentRepository.findAll();  // Retrieve all daily assignments
    }

    @Override
    public DailyAssignment save(DailyAssignment assignment) {
        return dailyAssignmentRepository.save(assignment);  // Save a daily assignment
    }

    @Override
    public DailyAssignment findById(Integer id) {
        return dailyAssignmentRepository.findById(id).orElse(null);  // Find a daily assignment by ID
    }

    @Override
    public void delete(Integer id) {
        dailyAssignmentRepository.deleteById(id);  // Delete a daily assignment by ID
    }

    @Override
    public DailyAssignment findByUserIdAndProductId(Integer userId, Integer productId) {
        List<DailyAssignment> assignments = dailyAssignmentRepository.findAll();

        // Iteramos sobre las asignaciones diarias para encontrar una coincidencia
        for (DailyAssignment assignment : assignments) {
            if (assignment.getUser().getId() == userId && assignment.getProduct().getId() == productId) {
                return assignment; // Retornamos la asignación si existe
            }
        }

        return null;
    }
}