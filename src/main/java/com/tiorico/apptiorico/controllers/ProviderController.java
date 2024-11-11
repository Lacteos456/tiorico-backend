package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ProviderDTO;
import com.tiorico.apptiorico.mappers.ProviderMapper;
import com.tiorico.apptiorico.models.Category;
import com.tiorico.apptiorico.models.Provider;
import com.tiorico.apptiorico.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/providers")
@CrossOrigin("*")
public class ProviderController
{
    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProviderMapper providerMapper;

    @GetMapping("/")
    public List<ProviderDTO> getAllProviders() {
        return providerService.findAll()
                .stream()
                .map(providerMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getProviderById(@PathVariable Integer id) {
        Provider provider = providerService.findById(id);
        if (provider == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(providerMapper.toDTO(provider));
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> createProvider(@RequestBody ProviderDTO providerDTO) {
        Provider provider = providerMapper.toEntity(providerDTO);
        Provider savedProvider = providerService.saveProvider(provider);
        return ResponseEntity.ok(providerMapper.toDTO(savedProvider));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> updateProvider(@PathVariable Integer id, @RequestBody ProviderDTO providerDTO) {
        Provider provider = providerService.findById(id);
        if (provider == null) {
            return ResponseEntity.notFound().build();
        }
        provider.setName(providerDTO.getName());
        provider.setContactInfo(providerDTO.getContactInfo());
        provider.setIsActive(providerDTO.getIsActive());
        provider.setCustomFields(providerDTO.getCustomFields());

        Provider updatedProvider = providerService.saveProvider(provider);
        return ResponseEntity.ok(providerMapper.toDTO(updatedProvider));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Integer id) {
        Provider provider = providerService.findById(id);
        if (provider != null) {
            provider.setIsActive(false); // Marcado como inactivo
            providerService.updateProvider(provider);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}