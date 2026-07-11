package com.edunest.service;

import com.edunest.configuration.JwtHelper;
import com.edunest.dto.auth.*;
import com.edunest.dto.teacher.TeacherResponse;
import com.edunest.entity.Teacher;
import com.edunest.entity.Tenant;
import com.edunest.error.CustomException;
import com.edunest.helper.CryptoHelper;
import com.edunest.repository.TeacherRepository;
import com.edunest.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    JwtHelper jwtHelper;


    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Teacher teacher = teacherRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow(() -> new CustomException("Teacher", "Teacher not found"));

        if (!teacher.getIsActive()) {
            throw new CustomException("Teacher", "Account is inactive. Please contact admin");
        }

        Tenant tenant = tenantRepository.findById(teacher.getTenantId()).orElseThrow(() -> new CustomException("Teacher", "Tenant not found"));

        String encryptedPassword = CryptoHelper.encryptPassword(loginRequest.getPassword(), teacher.getHashkey());
        if (!encryptedPassword.equals(teacher.getPassword())) {
            throw new CustomException("Teacher", "Invalid email or password");
        }

        teacher.setLastLogin(LocalDateTime.now());
        teacherRepository.save(teacher);

        String session = jwtHelper.generateAccessToken(teacher);
        String refresh = jwtHelper.generateRefreshToken(teacher);

        TenantResponse response = new TenantResponse();
        response.setTenantId(tenant.getTenantId());
        response.setFaviconUrl(tenant.getFaviconUrl());
        response.setLogoUrl(tenant.getLogoUrl());
        response.setTenantName(tenant.getTenantName());
        response.setSingleLogoUrl(tenant.getSingleLogoUrl());
        response.setPrimaryColor(tenant.getPrimaryColor());
        response.setFaviconUrl(tenant.getFaviconUrl());

        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setTeacherId(teacher.getTeacherId());
        teacherResponse.setTeacherName(teacherResponse.getTeacherName());
        teacherResponse.setEmail(teacher.getEmail());
        teacherResponse.setRoleId(teacher.getRoleId());
        teacherResponse.setEmail(teacher.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSession(session);
        loginResponse.setRefresh(refresh);
        loginResponse.setTeacher(teacherResponse);
        loginResponse.setTenant(response);

        return loginResponse;
    }

    public RenewSessionResponse renewSession(RenewSessionRequest request) {

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new CustomException("Teacher", "Teacher not found"));

        String newSession = jwtHelper.renewSessionJwt(teacher, request.getRefreshToken());

        return new RenewSessionResponse(new RenewSessionResponse.Token(newSession));
    }
}