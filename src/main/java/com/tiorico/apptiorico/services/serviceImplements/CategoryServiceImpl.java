package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.Category;
import com.tiorico.apptiorico.repositories.CategoryRepository;
import com.tiorico.apptiorico.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findByIsActiveTrue();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
}