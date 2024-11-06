package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Integer>
{
    List<ProductSupply> findByProductId(Integer productId);
}