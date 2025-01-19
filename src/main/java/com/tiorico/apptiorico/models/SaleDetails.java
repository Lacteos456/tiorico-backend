package com.tiorico.apptiorico.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_details")
public class SaleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "unit_quantity_sold", nullable = false)
    private Integer unitQuantitySold;

    @Column(name = "box_quantity_sold", nullable = false)
    private Integer boxQuantitySold;

    @Column(name = "unit_price", nullable = false, columnDefinition = "decimal(20,2) default 0.0")
    private Double unitPrice;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}