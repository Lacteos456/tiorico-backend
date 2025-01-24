package com.tiorico.apptiorico.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardData
{
    private long totalUsers;
    private long totalProducts;
    private long totalCategories;
    private double totalRevenue;
}