package com.edunest.dto.student;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentListResponse {
    private Integer studentId;
    private String admissionNo;
    private String studentName;
    private Character gender;
    private LocalDate dateOfBirth;
    private String mobileNo;
    private String email;
    private String className;
    private String sectionName;
    private String displayClass;
    private String rollNo;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private String fatherName;
    private String parentMobile;
    private LocalDateTime updatedDate;
    private String updatedBy;
}