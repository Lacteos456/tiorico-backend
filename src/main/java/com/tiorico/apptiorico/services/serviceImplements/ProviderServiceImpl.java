package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.models.Provider;
import com.tiorico.apptiorico.repositories.ProviderRepository;
import com.tiorico.apptiorico.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService
{
    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Provider findById(Integer id) {
        return providerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        providerRepository.deleteById(id);
    }
}