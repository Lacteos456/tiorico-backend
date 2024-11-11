package com.tiorico.apptiorico.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBoxSupplyDTO
{
    private Integer id;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Integer providerId;

    @NotNull(message = "El ID del producto es obligatorio")
    private Integer productId;

    @Min(value = 1, message = "La cantidad de cajas debe ser al menos 1")
    private Integer boxQuantity;

    @Min(value = 1, message = "Las unidades por caja deben ser al menos 1")
    private Integer unitsPerBox;

    @Min(value = 0, message = "El precio de la caja no puede ser negativo")
    private Double boxPrice;

    private Boolean isActive;

    private LocalDateTime supplyDate;
}