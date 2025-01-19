package com.tiorico.apptiorico.services.serviceImplements;

import com.tiorico.apptiorico.dtos.ForgotPasswordDTO;
import com.tiorico.apptiorico.dtos.ResetPasswordRequestDTO;
import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.repositories.UserRepository;
import com.tiorico.apptiorico.services.AuthService;
import com.tiorico.apptiorico.services.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService
{
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiration(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Reestablecer Clave de Seguridad", token);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO requestDTO) {
        User user = userRepository.findByResetToken(requestDTO.getToken())
                .orElseThrow(() -> new RuntimeException("Inválido o token expirado"));

        if (user.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token ha expirado");
        }

        user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);
        userRepository.save(user);
    }
}
