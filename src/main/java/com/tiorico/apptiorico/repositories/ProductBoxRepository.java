package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.ProductBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBoxRepository extends JpaRepository<ProductBox, Integer>
{
    List<ProductBox> findProductBoxByProduct_Id(Integer productId);
}