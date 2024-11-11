package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.Category;

import java.util.List;

public interface CategoryService
{
    List<Category> findAll();
    List<Category> findActiveCategories();
    Category save(Category category);
    Category findById(Integer id);
    Category updateCategory(Category category);
}