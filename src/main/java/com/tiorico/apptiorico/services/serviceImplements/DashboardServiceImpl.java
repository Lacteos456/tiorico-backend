package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.DashboardData;
import com.tiorico.apptiorico.repositories.CategoryRepository;
import com.tiorico.apptiorico.repositories.ProductRepository;
import com.tiorico.apptiorico.repositories.UserRepository;
import com.tiorico.apptiorico.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService
{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DashboardServiceImpl(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public DashboardData getDashboardData() {
        long totalUsers = userRepository.count(); // Contar usuarios
        long totalProducts = productRepository.count(); // Contar productos
        long totalCategories = categoryRepository.count(); // Contar categorias

        return new DashboardData(totalUsers, totalProducts, totalCategories);
    }
}