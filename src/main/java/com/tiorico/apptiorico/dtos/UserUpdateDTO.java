package com.tiorico.apptiorico.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO
{
    private Integer id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    // La contraseña es opcional aquí
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String phone;

    private Boolean isActive;
}