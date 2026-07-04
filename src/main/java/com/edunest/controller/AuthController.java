package com.edunest.controller;

import com.edunest.common.ResponseObject;
import com.edunest.dto.LoginRequest;
import com.edunest.dto.LoginResponse;
import com.edunest.dto.RenewSessionRequest;
import com.edunest.dto.RenewSessionResponse;
import com.edunest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ResponseObject<LoginResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(authService.login(loginRequest));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/renew-session")
    public ResponseEntity<ResponseObject<RenewSessionResponse>> renewSession(
            @RequestBody RenewSessionRequest request) {
        System.out.println("harshid");
        ResponseObject<RenewSessionResponse> response = new ResponseObject<>();
        response.setSuccess(true);
        response.setData(authService.renewSession(request));

        return ResponseEntity.ok(response);
    }
}
