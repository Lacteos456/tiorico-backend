package com.tiorico.apptiorico.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBoxDTO
{
    private Integer id;

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productId;

    @Min(value = 0, message = "El precio de la caja no puede ser negativo")
    private Double boxPrice;

    private Integer unitsPerBox;

    private Boolean isActive = true;
}