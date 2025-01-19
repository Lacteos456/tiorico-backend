package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetails, Integer>
{

}