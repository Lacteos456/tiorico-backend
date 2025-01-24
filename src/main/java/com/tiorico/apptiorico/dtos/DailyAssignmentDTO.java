package com.tiorico.apptiorico.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyAssignmentDTO {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer productBoxSupplyId;
    private LocalDate assignmentDate;
    private Integer assignedBoxes;
    private Integer assignedUnits;
    private Integer totalBoxes;
    private Integer totalUnits;
    private Integer soldUnits;
    private Integer soldBoxes;
    private Integer returnedUnits;
    private Boolean isUsed;
    private Integer returnedBoxes;
    private Integer remainingUnits;
    private Double totalRevenue;
    private Boolean isActive;
}