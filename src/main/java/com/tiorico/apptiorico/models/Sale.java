package com.tiorico.apptiorico.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "daily_assignment_id", nullable = false)
    private DailyAssignment dailyAssignment;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    @Column(name = "price", nullable = false, columnDefinition = "decimal(20,2) default 0.0")
    private Double price;

    @Column(name = "total_price", nullable = false, columnDefinition = "decimal(20,2) default 0.0")
    private Double totalPrice;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleDetails> saleDetails = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}