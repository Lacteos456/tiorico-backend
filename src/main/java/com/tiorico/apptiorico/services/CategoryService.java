package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.Category;

import java.util.List;

public interface CategoryService
{
    List<Category> findAll();
    Category save(Category category);
}