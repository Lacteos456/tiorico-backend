package com.tiorico.apptiorico.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "daily_assignments")
public class DailyAssignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "assigned_quantity", columnDefinition = "int default 0")
    private Integer assignedQuantity;

    @Column(name = "returned_boxes", columnDefinition = "int default 0")
    private Integer returnedBoxes = 0;

    @Column(name = "returned_units", columnDefinition = "int default 0")
    private Integer returnedUnits = 0;

    @Column(name = "total_sold_units", columnDefinition = "int default 0")
    private Integer totalSoldUnits = 0;

    @Column(name = "total_revenue", columnDefinition = "decimal(20,2) default 0.0")
    private Double totalRevenue = 0.0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isActive;

    @Lob
    @Column(name = "custom_fields")
    private String customFields;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}