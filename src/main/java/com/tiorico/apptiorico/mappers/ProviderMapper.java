package com.tiorico.apptiorico.mappers;

import com.tiorico.apptiorico.dtos.ProviderDTO;
import com.tiorico.apptiorico.models.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapper
{
    public ProviderDTO toDTO(Provider provider) {
        return new ProviderDTO(provider.getId(), provider.getName(), provider.getContactInfo(), provider.getIsActive(), provider.getCustomFields());
    }

    public Provider toEntity(ProviderDTO dto) {
        Provider provider = new Provider();
        provider.setId(dto.getId());
        provider.setName(dto.getName());
        provider.setContactInfo(dto.getContactInfo());
        provider.setIsActive(dto.getIsActive());
        provider.setCustomFields(dto.getCustomFields());
        return provider;
    }
}