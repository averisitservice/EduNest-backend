package com.edunest.controller;

import com.edunest.common.ResponseObject;
import com.edunest.configuration.JwtHelper;
import com.edunest.dto.auth.ForgotPasswordRequest;
import com.edunest.dto.auth.LoginRequest;
import com.edunest.dto.auth.LoginResponse;
import com.edunest.dto.auth.RenewSessionRequest;
import com.edunest.dto.auth.RenewSessionResponse;
import com.edunest.dto.auth.ResetPasswordRequest;
import com.edunest.dto.auth.SchoolLookupResponse;
import com.edunest.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    JwtHelper jwtHelper;

    @GetMapping("/tenant/{schoolCode}")
    public ResponseEntity<ResponseObject<SchoolLookupResponse>> getTenantBySchoolCode(@PathVariable String schoolCode) {
        ResponseObject<SchoolLookupResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(authService.getTenantBySchoolCode(schoolCode));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ResponseObject<LoginResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(authService.login(loginRequest));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseObject<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);

        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData("A new password has been sent to your registered email address.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseObject<String>> resetPassword(
            HttpServletRequest httpRequest, @RequestBody ResetPasswordRequest request) {

        String token = jwtHelper.cleanToken(httpRequest.getHeader(HttpHeaders.AUTHORIZATION));
        Integer loginTeacherId = jwtHelper.extractTeacherId(token);

        authService.resetPassword(loginTeacherId, request);

        ResponseObject<String> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData("Your password has been changed successfully.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/renew-session")
    public ResponseEntity<ResponseObject<RenewSessionResponse>> renewSession(@RequestBody RenewSessionRequest request) {
        ResponseObject<RenewSessionResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(authService.renewSession(request));

        return ResponseEntity.ok(response);
    }
}
