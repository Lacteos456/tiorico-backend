package com.tiorico.apptiorico.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO
{
    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar en blanco")
    @Size(min = 3, max = 100, message = "El nombre del producto debe tener entre 3 y 100 caracteres")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private double price;

    @Min(value = 0, message = "El stock no puede ser menor a 0")
    private Integer stock;

    @NotBlank(message = "El campo de la imagen no puede estar vacío")
    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "El producto debe tener un estado activo")
    private Boolean isActive;

    @Size(max = 500, message = "Los campos personalizados no pueden exceder los 500 caracteres")
    private String customFields;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Integer categoryId;
}