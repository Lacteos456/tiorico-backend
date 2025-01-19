package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.AdminDashboardData;
import com.tiorico.apptiorico.models.SellerDashboardData;

public interface DashboardService
{
    public AdminDashboardData getDashboardData();
    public SellerDashboardData getSellerDashboardData(Integer sellerId);
}