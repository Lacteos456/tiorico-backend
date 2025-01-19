package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.models.User;
import org.springframework.stereotype.Component;

@Component
public class DailyAssignmentMapper {
    public DailyAssignmentDTO toDTO(DailyAssignment assignment) {
        return new DailyAssignmentDTO(
                assignment.getId(),
                assignment.getUser().getId(),
                assignment.getProduct().getId(),
                assignment.getProductBoxSupply().getId(),
                assignment.getAssignmentDate(),
                assignment.getAssignedBoxes(),
                assignment.getSoldUnits(),
                assignment.getSoldBoxes(),
                assignment.getReturnedUnits(),
                assignment.getReturnedBoxes(),
                assignment.getRemainingUnits(),
                assignment.getTotalRevenue(),
                assignment.getIsActive()
        );
    }

    public DailyAssignment toEntity(DailyAssignmentDTO dto, User user, Product product, ProductBoxSupply productBoxSupply) {
        return new DailyAssignment(
                dto.getId(),
                user,
                product,
                productBoxSupply,
                dto.getAssignmentDate(),
                dto.getAssignedBoxes() != null ? dto.getAssignedBoxes() : 0,
                dto.getSoldUnits() != null ? dto.getSoldUnits() : 0,
                dto.getSoldBoxes() != null ? dto.getSoldBoxes() : 0,
                dto.getReturnedUnits() != null ? dto.getReturnedUnits() : 0,
                dto.getReturnedBoxes() != null ? dto.getReturnedBoxes() : 0,
                dto.getRemainingUnits() != null ? dto.getRemainingUnits() : 0,
                dto.getTotalRevenue() != null ? dto.getTotalRevenue() : 0.0,
                dto.getIsActive() != null ? dto.getIsActive() : true
        );
    }
}