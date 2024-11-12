package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductBoxSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBoxSupplyRepository extends JpaRepository<ProductBoxSupply, Integer>
{
    @Query("SELECT p FROM ProductBoxSupply p WHERE p.isActive = true")
    List<ProductBoxSupply> findAllActive();

    @Query("SELECT pbs.unitsPerBox FROM ProductBoxSupply pbs WHERE pbs.product.id = :productId AND pbs.isActive = true")
    List<Integer> findUnitsPerBoxByProductId(@Param("productId") Integer productId);

    @Query("SELECT pbs.boxQuantity FROM ProductBoxSupply pbs WHERE pbs.product.id = :productId AND pbs.isActive = true")
    List<Integer> findUnitsBoxQuantityByProductId(@Param("productId") Integer productId);

    @Query("SELECT COUNT(pbs) FROM ProductBoxSupply pbs WHERE pbs.product.id = :productId AND pbs.isActive = true")
    Integer findCountsByProductId(@Param("productId") Integer productId);
}