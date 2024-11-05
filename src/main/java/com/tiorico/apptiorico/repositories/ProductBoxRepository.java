package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBoxRepository extends JpaRepository<ProductBox, Integer>
{

}