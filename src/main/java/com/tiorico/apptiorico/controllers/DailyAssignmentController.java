package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.services.DailyAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/daily-assignments")
@CrossOrigin("*")
public class DailyAssignmentController
{
    @Autowired
    private DailyAssignmentService dailyAssignmentService;

    @PostMapping
    public ResponseEntity<DailyAssignmentDTO> createDailyAssignment(@RequestBody DailyAssignmentDTO dto) {
        DailyAssignmentDTO createdAssignment = dailyAssignmentService.createDailyAssignment(dto);
        return ResponseEntity.ok(createdAssignment);
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<DailyAssignmentDTO>> getAssignmentsByDate(@PathVariable LocalDate date) {
        List<DailyAssignmentDTO> assignments = dailyAssignmentService.getAssignmentsByDate(date);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DailyAssignmentDTO> getDailyAssignmentById(@PathVariable Integer id) {
        DailyAssignmentDTO assignment = dailyAssignmentService.getDailyAssignmentById(id);
        return ResponseEntity.ok(assignment);
    }

    @GetMapping
    public ResponseEntity<List<DailyAssignmentDTO>> getAllAssignments() {
        List<DailyAssignmentDTO> assignments = dailyAssignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyAssignmentDTO> updateDailyAssignment(@PathVariable Integer id, @RequestBody DailyAssignmentDTO dto) {
        DailyAssignmentDTO updatedAssignment = dailyAssignmentService.updateDailyAssignment(id, dto);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyAssignment(@PathVariable Integer id) {
        dailyAssignmentService.deleteDailyAssignment(id);
        return ResponseEntity.noContent().build();
    }
}