package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.AdminDashboardData;
import com.tiorico.apptiorico.models.SellerDashboardData;
import com.tiorico.apptiorico.repositories.*;
import com.tiorico.apptiorico.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService
{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DailyAssignmentRepository dailyAssignmentRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public DashboardServiceImpl(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository, DailyAssignmentRepository dailyAssignmentRepository, SaleRepository saleRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dailyAssignmentRepository = dailyAssignmentRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public AdminDashboardData getDashboardData() {
        long totalUsers = userRepository.count(); // Contar usuarios
        long totalProducts = productRepository.count(); // Contar productos
        long totalCategories = categoryRepository.count(); // Contar categorias
        Double totalRevenue = saleRepository.calculateTotalRevenue(); // Contar ventas

        return new AdminDashboardData(
                totalUsers,
                totalProducts,
                totalCategories,
                totalRevenue != null ? totalRevenue : 0D
        );
    }

    @Override
    public SellerDashboardData getSellerDashboardData(Integer sellerId) {
        long totalAssignments = dailyAssignmentRepository.countByUserId(sellerId);
        long totalSales = saleRepository.countByUserId(sellerId);
        Double totalRevenue = saleRepository.sumRevenueByUserId(sellerId);

        return new SellerDashboardData(
                totalAssignments,
                totalSales,
                totalRevenue != null ? totalRevenue : 0.0
        );
    }
}