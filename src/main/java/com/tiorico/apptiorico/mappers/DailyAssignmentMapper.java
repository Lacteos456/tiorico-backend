package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.User;
import org.springframework.stereotype.Component;

@Component
public class DailyAssignmentMapper
{
    // Convert DailyAssignment to DailyAssignmentDTO
    public DailyAssignmentDTO toDTO(DailyAssignment assignment) {
        if (assignment == null) {
            return null;
        }

        DailyAssignmentDTO dto = new DailyAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setUserId(assignment.getUser() != null ? assignment.getUser().getId() : null);
        dto.setProductId(assignment.getProduct() != null ? assignment.getProduct().getId() : null); // Manejo de producto nulo
        dto.setDate(assignment.getDate());
        dto.setAssignedQuantity(assignment.getAssignedQuantity());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setUpdatedAt(assignment.getUpdatedAt());
        dto.setIsActive(assignment.getIsActive());
        dto.setCustomFields(assignment.getCustomFields());

        return dto;
    }

    // Convert DailyAssignmentDTO to DailyAssignment
    public DailyAssignment toEntity(DailyAssignmentDTO dto, User user, Product product) {
        if (dto == null) {
            return null;
        }

        DailyAssignment assignment = new DailyAssignment();
        assignment.setUser(user); // Asigna el objeto User
        assignment.setProduct(product); // Asigna el objeto Product
        assignment.setDate(dto.getDate());
        assignment.setAssignedQuantity(dto.getAssignedQuantity());
        assignment.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        assignment.setCustomFields(dto.getCustomFields());

        return assignment;
    }
}