package com.edunest.service;

import com.edunest.dto.auth.ForgotPasswordRequest;
import com.edunest.dto.auth.LoginRequest;
import com.edunest.dto.auth.LoginResponse;
import com.edunest.dto.auth.RenewSessionRequest;
import com.edunest.dto.auth.RenewSessionResponse;
import com.edunest.dto.auth.ResetPasswordRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    RenewSessionResponse renewSession(RenewSessionRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(Integer teacherId, ResetPasswordRequest request);
}
