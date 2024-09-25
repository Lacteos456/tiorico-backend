package com.tiorico.apptiorico.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO
{
	@NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
    
	@NotBlank(message = "La contraseña no puede estar vacía")
	@Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    
	@NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
	@NotBlank(message = "El teléfono no puede estar vacío")
    private String phone;
}
