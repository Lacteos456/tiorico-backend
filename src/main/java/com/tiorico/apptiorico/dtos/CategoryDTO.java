package com.tiorico.apptiorico.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO
{
    private Integer id;

    @NotBlank(message = "El nombre de la categoría no puede estar en blanco")
    @Size(min = 3, max = 50, message = "El nombre de la categoría debe tener entre 3 y 50 caracteres")
    private String name;

    @Size(max = 200, message = "La descripción no puede exceder los 200 caracteres")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Integer> productIds;

    private Boolean isActive;

    @Size(max = 500, message = "Los campos personalizados no pueden exceder los 500 caracteres")
    private String customFields;
}