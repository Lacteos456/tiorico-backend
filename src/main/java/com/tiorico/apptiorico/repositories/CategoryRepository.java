package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>
{
}