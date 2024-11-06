package com.tiorico.apptiorico.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupplyDTO
{
    private Integer id;
    private Integer providerId;
    private Integer productId;
    private Integer supplyQuantity;
    private Double supplyPrice;
    private LocalDateTime supplyDate;
}