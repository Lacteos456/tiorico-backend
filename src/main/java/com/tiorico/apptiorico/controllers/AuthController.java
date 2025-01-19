package com.tiorico.apptiorico.controllers;

import com.tiorico.apptiorico.dtos.ForgotPasswordDTO;
import com.tiorico.apptiorico.dtos.ResetPasswordRequestDTO;
import com.tiorico.apptiorico.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin("*")
public class AuthController
{
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody ForgotPasswordDTO requestDTO) {
        authService.sendForgotPasswordEmail(requestDTO);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "El correo para reestablecer se ha enviado correctamente"
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequestDTO requestDTO) {
        authService.resetPassword(requestDTO);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "La contraseña ha sido cambiada correctamente"
        ));
    }
}