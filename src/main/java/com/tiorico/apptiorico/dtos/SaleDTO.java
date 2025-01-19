package com.tiorico.apptiorico.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Integer id;
    private Integer userId;
    private Integer dailyAssignmentId;
    private LocalDate saleDate;
    private Double totalPrice;
    private Double price;
    private Boolean isActive;
    private List<SaleDetailsDTO> saleDetails;
}