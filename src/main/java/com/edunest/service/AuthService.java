package com.edunest.service;

import com.edunest.dto.auth.LoginRequest;
import com.edunest.dto.auth.LoginResponse;
import com.edunest.dto.auth.RenewSessionRequest;
import com.edunest.dto.auth.RenewSessionResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    RenewSessionResponse renewSession(RenewSessionRequest request);
}
