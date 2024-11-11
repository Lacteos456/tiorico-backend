package com.tiorico.apptiorico.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyAssignmentDTO
{
    private Integer id;

    @NotNull(message = "El ID del usuario no puede estar vacío")
    private Integer userId;

    @NotNull(message = "El ID del producto no puede estar vacío")
    private Integer productId;

    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate date;

    private Integer assignedQuantity;

    private Integer returnedBoxes = 0;

    private Integer returnedUnits = 0;

    private Integer totalSoldUnits = 0;

    private Double totalRevenue = 0.0;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isActive;

    @Size(max = 500, message = "Los campos personalizados no pueden exceder los 500 caracteres")
    private String customFields;
}