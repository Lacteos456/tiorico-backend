package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductBoxSupply;
import com.tiorico.apptiorico.models.ProductSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBoxSupplyRepository extends JpaRepository<ProductBoxSupply, Integer>
{
    List<ProductBoxSupply> findByProductBoxId(Integer productBoxId);
}