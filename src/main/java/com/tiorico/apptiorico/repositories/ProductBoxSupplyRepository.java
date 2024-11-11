package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductBoxSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBoxSupplyRepository extends JpaRepository<ProductBoxSupply, Integer>
{
}