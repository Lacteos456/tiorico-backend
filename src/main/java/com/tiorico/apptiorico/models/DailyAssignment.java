package com.tiorico.apptiorico.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_assignments")
public class DailyAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_box_supply_id", nullable = false)
    private ProductBoxSupply productBoxSupply;

    @Column(name = "assignment_date", nullable = false)
    private LocalDate assignmentDate;

    @Column(name = "assigned_boxes", nullable = false)
    private Integer assignedBoxes;

    @Column(name = "assigned_units", nullable = false)
    private Integer assignedUnits;

    @Column(name = "total_boxes", nullable = false)
    private Integer totalBoxes;

    @Column(name = "total_units", nullable = false)
    private Integer totalUnits;

    @Column(name = "sold_units", nullable = false)
    private Integer soldUnits = 0;

    @Column(name = "sold_boxes", nullable = false)
    private Integer soldBoxes = 0;

    @Column(name = "returned_units", nullable = false)
    private Integer returnedUnits = 0;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = true;

    @Column(name = "returned_boxes", nullable = false)
    private Integer returnedBoxes = 0;

    @Column(name = "remaining_units", nullable = false)
    private Integer remainingUnits = 0;

    @Column(name = "total_revenue", nullable = false, columnDefinition = "decimal(20,2) default 0.0")
    private Double totalRevenue = 0.0;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PostPersist
    public void calculateRevenue() {
        totalRevenue = soldUnits * product.getPrice();
    }
}