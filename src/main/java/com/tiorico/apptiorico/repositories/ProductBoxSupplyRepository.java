package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductBoxSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBoxSupplyRepository extends JpaRepository<ProductBoxSupply, Integer>
{
    @Query("SELECT p FROM ProductBoxSupply p WHERE p.isActive = true")
    List<ProductBoxSupply> findAllActive();
}