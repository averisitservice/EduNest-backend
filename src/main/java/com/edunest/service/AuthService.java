package com.edunest.service;

import com.edunest.dto.LoginRequest;
import com.edunest.dto.LoginResponse;
import com.edunest.dto.RenewSessionRequest;
import com.edunest.dto.RenewSessionResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    RenewSessionResponse renewSession(RenewSessionRequest request);
}
