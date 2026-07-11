package com.edunest.dto.teacher;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherListResponse {
    private int teacherId;
    private int roleId;
    private int employmentId;
    private String mobileNo;
    private String email;
    private String teacherName;
    private LocalDateTime lastLogin;
    private LocalDateTime updatedDate;
    private String updatedBy;
}