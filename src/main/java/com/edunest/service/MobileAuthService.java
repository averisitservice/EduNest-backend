package com.edunest.service;

import com.edunest.dto.mobile.StudentLoginRequest;
import com.edunest.dto.mobile.StudentLoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface MobileAuthService {
    StudentLoginResponse studentLogin(StudentLoginRequest request);
}
