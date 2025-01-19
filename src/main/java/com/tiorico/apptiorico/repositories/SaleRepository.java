package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>
{
    List<Sale> findByUserId(Integer userId);
}