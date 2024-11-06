package com.tiorico.apptiorico.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDTO
{
    private Integer id;
    private String name;
    private String contactInfo;
    private Boolean isActive;
    private String customFields;
}