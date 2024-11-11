package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.models.DailyAssignment;
import org.springframework.stereotype.Component;

@Component
public class DailyAssignmentMapper
{
    public DailyAssignment toEntity(DailyAssignmentDTO dto) {
        DailyAssignment assignment = new DailyAssignment();
        assignment.setId(dto.getId());
        assignment.setDate(dto.getDate());
        assignment.setAssignedQuantity(dto.getAssignedQuantity());
        assignment.setReturnedBoxes(dto.getReturnedBoxes());
        assignment.setReturnedUnits(dto.getReturnedUnits());
        assignment.setTotalSoldUnits(dto.getTotalSoldUnits());
        assignment.setTotalRevenue(dto.getTotalRevenue());
        assignment.setCreatedAt(dto.getCreatedAt());
        assignment.setUpdatedAt(dto.getUpdatedAt());
        assignment.setIsActive(dto.getIsActive());
        assignment.setCustomFields(dto.getCustomFields());
        return assignment;
    }

    public DailyAssignmentDTO toDTO(DailyAssignment assignment) {
        DailyAssignmentDTO dto = new DailyAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setUserId(assignment.getUser().getId());
        dto.setProductId(assignment.getProduct().getId());
        dto.setDate(assignment.getDate());
        dto.setAssignedQuantity(assignment.getAssignedQuantity());
        dto.setReturnedBoxes(assignment.getReturnedBoxes());
        dto.setReturnedUnits(assignment.getReturnedUnits());
        dto.setTotalSoldUnits(assignment.getTotalSoldUnits());
        dto.setTotalRevenue(assignment.getTotalRevenue());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setUpdatedAt(assignment.getUpdatedAt());
        dto.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        dto.setCustomFields(assignment.getCustomFields());
        return dto;
    }
}