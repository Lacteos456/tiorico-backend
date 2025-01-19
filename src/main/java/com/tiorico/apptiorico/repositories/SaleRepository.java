package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>
{
    List<Sale> findByUserId(Integer userId);
    long countByUserId(long userId);

    @Query("SELECT SUM(s.totalPrice) FROM Sale s WHERE s.user.id = :userId")
    Double sumRevenueByUserId(@Param("userId") long userId);
}