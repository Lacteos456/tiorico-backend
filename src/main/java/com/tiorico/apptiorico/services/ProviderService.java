package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.models.Provider;

import java.util.List;

public interface ProviderService
{
    Provider saveProvider(Provider provider);
    Provider findById(Integer id);
    List<Provider> findAll();
    void deleteById(Integer id);
}