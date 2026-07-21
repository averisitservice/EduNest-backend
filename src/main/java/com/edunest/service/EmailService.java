package com.edunest.service;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String teacherName, String newPassword);
}
