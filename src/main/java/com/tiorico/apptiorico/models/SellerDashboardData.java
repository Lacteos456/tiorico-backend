package com.tiorico.apptiorico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDashboardData
{
    private long totalAssignments;
    private long totalSales;
    private double totalRevenue;
}