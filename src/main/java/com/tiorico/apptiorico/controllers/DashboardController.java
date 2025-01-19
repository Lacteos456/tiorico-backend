package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.models.AdminDashboardData;
import com.tiorico.apptiorico.models.SellerDashboardData;
import com.tiorico.apptiorico.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/dashboard")
@CrossOrigin("*")
public class DashboardController
{
    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/admin")
    public ResponseEntity<AdminDashboardData> getDashboardData() {
        AdminDashboardData dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<SellerDashboardData> getSellerDashboardData(@PathVariable Integer sellerId) {
        SellerDashboardData dashboardData = dashboardService.getSellerDashboardData(sellerId);
        return ResponseEntity.ok(dashboardData);
    }
}