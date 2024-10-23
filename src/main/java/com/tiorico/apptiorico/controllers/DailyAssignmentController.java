package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.DailyAssignmentDTO;
import com.tiorico.apptiorico.mappers.DailyAssignmentMapper;
import com.tiorico.apptiorico.models.DailyAssignment;
import com.tiorico.apptiorico.models.Product;
import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.services.DailyAssignmentService;
import com.tiorico.apptiorico.services.ProductService;
import com.tiorico.apptiorico.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/daily-assignments")
@CrossOrigin("*")
public class DailyAssignmentController
{
    @Autowired
    private DailyAssignmentService dailyAssignmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private DailyAssignmentMapper dailyAssignmentMapper;

    // Get all daily assignments
    @GetMapping("/")
    public ResponseEntity<List<DailyAssignmentDTO>> getDailyAssignments() {
        List<DailyAssignment> assignments = dailyAssignmentService.findAll();
        List<DailyAssignmentDTO> assignmentDTOs = assignments.stream()
                .map(dailyAssignmentMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(assignmentDTOs);
    }

    // Get a daily assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<DailyAssignmentDTO> getDailyAssignmentById(@PathVariable Integer id) {
        DailyAssignment assignment = dailyAssignmentService.findById(id);
        if (assignment != null) {
            return ResponseEntity.ok(dailyAssignmentMapper.toDTO(assignment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<DailyAssignmentDTO> createDailyAssignment(
            @PathVariable Integer userId,
            @PathVariable Integer productId,
            @RequestBody DailyAssignmentDTO assignmentDTO) {

        // Aquí debes buscar los objetos User y Product usando sus IDs
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        if (user == null || product == null) {
            return ResponseEntity.badRequest().build();
        }

        // Verificar si ya existe una asignación diaria para el mismo usuario y producto
        DailyAssignment existingAssignment = dailyAssignmentService.findByUserIdAndProductId(userId, productId);
        if (existingAssignment != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        DailyAssignment assignment = dailyAssignmentMapper.toEntity(assignmentDTO, user, product);
        DailyAssignment savedAssignment = dailyAssignmentService.save(assignment);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dailyAssignmentMapper.toDTO(savedAssignment));
    }

    // Update an existing daily assignment
    @PutMapping("/{id}/{userId}/{productId}")
    public ResponseEntity<DailyAssignmentDTO> updateDailyAssignment(
            @PathVariable Integer id,
            @PathVariable Integer userId,
            @PathVariable Integer productId,
            @RequestBody DailyAssignmentDTO assignmentDTO) {

        // Verificar si el usuario y el producto existen
        User user = userService.getUserById(userId);
        Product product = productService.getProductById(productId);

        if (user == null || product == null) {
            return ResponseEntity.badRequest().build();
        }

        // Verificar si la asignación diaria existe
        DailyAssignment existingAssignment = dailyAssignmentService.findById(id);
        if (existingAssignment == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        // Verificar si ya existe una asignación diaria para el mismo usuario y producto
        DailyAssignment duplicateAssignment = dailyAssignmentService.findByUserIdAndProductId(userId, productId);
        if (duplicateAssignment != null && duplicateAssignment.getId() != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        // Actualizar la asignación diaria
        existingAssignment.setAssignedQuantity(assignmentDTO.getAssignedQuantity());
        existingAssignment.setCustomFields(assignmentDTO.getCustomFields());
        existingAssignment.setDate(assignmentDTO.getDate());
        existingAssignment.setUser(user);
        existingAssignment.setProduct(product);

        DailyAssignment updatedAssignment = dailyAssignmentService.save(existingAssignment);

        return ResponseEntity.ok(dailyAssignmentMapper.toDTO(updatedAssignment));
    }

    // Delete a daily assignment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDailyAssignment(@PathVariable Integer id) {
        dailyAssignmentService.delete(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}