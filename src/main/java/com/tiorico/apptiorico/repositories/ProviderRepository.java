package com.tiorico.apptiorico.repositories;

import com.tiorico.apptiorico.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer>
{
    List<Provider> findByIsActiveTrue();
}