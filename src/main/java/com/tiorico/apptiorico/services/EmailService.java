package com.tiorico.apptiorico.services;

public interface EmailService
{
    void sendEmail(String to, String subject, String token);
}