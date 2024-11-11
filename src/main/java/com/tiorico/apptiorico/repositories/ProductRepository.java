package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>
{
    List<Product> findByIsActiveTrue();
}