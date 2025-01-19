package com.tiorico.apptiorico.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailsDTO {
    private Integer id;
    private Integer saleId;
    private Integer productId;
    private Integer unitQuantitySold;
    private Integer boxQuantitySold;
    private Double unitPrice;
    private Boolean isActive;
}