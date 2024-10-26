package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.models.DashboardData;
import com.tiorico.apptiorico.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<DashboardData> getDashboardData() {
        DashboardData dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}