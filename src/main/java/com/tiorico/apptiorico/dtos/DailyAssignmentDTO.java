package com.tiorico.apptiorico.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime date;

    private Integer assignedQuantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isActive;

    @Size(max = 500, message = "Los campos personalizados no pueden exceder los 500 caracteres")
    private String customFields;
}