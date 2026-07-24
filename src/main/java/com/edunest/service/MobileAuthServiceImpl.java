package com.edunest.service;

import com.edunest.configuration.JwtHelper;
import com.edunest.dto.auth.TenantResponse;
import com.edunest.dto.mobile.StudentForgotPasswordRequest;
import com.edunest.dto.mobile.StudentLoginRequest;
import com.edunest.dto.mobile.StudentLoginResponse;
import com.edunest.dto.mobile.StudentProfileResponse;
import com.edunest.dto.mobile.StudentResetCredential;
import com.edunest.entity.Student;
import com.edunest.entity.Tenant;
import com.edunest.error.CustomException;
import com.edunest.helper.CryptoHelper;
import com.edunest.repository.StudentRepository;
import com.edunest.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MobileAuthServiceImpl implements MobileAuthService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TenantRepository tenantRepository;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    EmailService emailService;

    @Override
    public StudentLoginResponse studentLogin(StudentLoginRequest request) {
        Student student = studentRepository.findByUsernameIgnoreCase(request.getUsername().trim())
                .orElseThrow(() -> new CustomException("username", "Invalid username or password"));

        String encryptedPassword = CryptoHelper.encryptPassword(request.getPassword(), student.getHashkey());
        if (!encryptedPassword.equals(student.getPassword())) {
            throw new CustomException("password", "Invalid username or password");
        }

        if (Boolean.FALSE.equals(student.getIsActive())) {
            throw new CustomException("username", "Account is inactive. Please contact your school");
        }

        Tenant tenant = tenantRepository.findById(student.getTenantId())
                .orElseThrow(() -> new CustomException("tenant", "School not found"));

        if (Boolean.FALSE.equals(tenant.getIsActive())) {
            throw new CustomException("tenant", "This school is no longer active");
        }

        student.setLastLogin(LocalDateTime.now());
        studentRepository.save(student);

        String session = jwtHelper.generateStudentAccessToken(student);
        String refresh = jwtHelper.generateStudentRefreshToken(student);

        StudentProfileResponse profile = new StudentProfileResponse();
        profile.setStudentId(student.getStudentId());
        profile.setAdmissionNo(student.getAdmissionNo());
        profile.setUsername(student.getUsername());
        profile.setStudentName(buildStudentName(student));
        profile.setEmail(student.getEmail());
        profile.setMobileNo(student.getMobileNo());
        profile.setPhotoUrl(student.getPhotoUrl());
        profile.setIsHostel(student.getIsHostel());

        TenantResponse tenantResponse = new TenantResponse();
        tenantResponse.setTenantId(tenant.getTenantId());
        tenantResponse.setSchoolCode(tenant.getSchoolCode());
        tenantResponse.setTenantName(tenant.getTenantName());
        tenantResponse.setSchoolBannerUrl(tenant.getSchoolBannerUrl());
        tenantResponse.setMobileLogoUrl(tenant.getMobileLogoUrl());
        tenantResponse.setLogoUrl(tenant.getLogoUrl());
        tenantResponse.setSingleLogoUrl(tenant.getSingleLogoUrl());
        tenantResponse.setPrimaryColor(tenant.getPrimaryColor());
        tenantResponse.setFaviconUrl(tenant.getFaviconUrl());
        tenantResponse.setIsHostel(tenant.getIsHostel());

        log.info("Student {} logged in from mobile", student.getStudentId());

        return new StudentLoginResponse(session, refresh, profile, tenantResponse);
    }

    @Override
    @Transactional
    public void forgotPassword(StudentForgotPasswordRequest request) {

        String email = request.getEmail().trim();

        List<Student> students = studentRepository.findByEmailIgnoreCaseAndIsActiveTrue(email);
        if (students.isEmpty()) {
            throw new CustomException("email", "No account found with this email");
        }

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();

        List<StudentResetCredential> accounts = new ArrayList<>();

        for (Student student : students) {
            String newPassword = generator.generate(8);
            String hashKey = CryptoHelper.getHashKey();

            student.setHashkey(hashKey);
            student.setPassword(CryptoHelper.encryptPassword(newPassword, hashKey));
            studentRepository.save(student);

            accounts.add(new StudentResetCredential(
                    buildStudentName(student), student.getUsername(), newPassword));
        }

        emailService.sendStudentPasswordResetEmail(email, accounts);

        log.info("Password reset for {} student account(s) on email {}", accounts.size(), email);
    }

    private String buildStudentName(Student student) {
        StringBuilder name = new StringBuilder();
        if (student.getFirstName() != null) name.append(student.getFirstName());
        if (student.getMiddleName() != null && !student.getMiddleName().isBlank()) {
            name.append(" ").append(student.getMiddleName());
        }
        if (student.getLastName() != null) name.append(" ").append(student.getLastName());
        return name.toString().trim();
    }
}
