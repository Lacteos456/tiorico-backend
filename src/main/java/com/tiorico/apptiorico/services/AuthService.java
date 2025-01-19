package com.tiorico.apptiorico.services;

import com.tiorico.apptiorico.dtos.ForgotPasswordDTO;
import com.tiorico.apptiorico.dtos.ResetPasswordRequestDTO;

public interface AuthService
{
    void sendForgotPasswordEmail(ForgotPasswordDTO requestDTO);
    void resetPassword(ResetPasswordRequestDTO requestDTO);
}