package com.edunest.dto.mobile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Full student profile shown on the mobile Profile screen. Richer than
 * {@link StudentProfileResponse}, which only carries what login needs.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailResponse {

    private Integer studentId;
    private String admissionNo;
    private String username;
    private String studentName;
    private String photoUrl;

    // Personal
    private LocalDate dateOfBirth;
    private String gender;
    private String aadharNo;
    private String email;
    private String mobileNo;
    private Boolean isHostel;

    // Class placement
    private Integer classId;
    private String className;
    private Integer sectionId;
    private String sectionName;
    private String displayClass;
    private String rollNo;
    private String classTeacherName;

    // Parents
    private String fatherName;
    private String motherName;
    private String parentMobile;
    private String parentEmail;
    private String parentAadhar;

    // Full address on a single line, Indian format:
    // "<line1>, <city>, <state> - <pincode>"
    private String address;
}
