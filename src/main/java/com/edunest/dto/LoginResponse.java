package com.edunest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String session;
    private String refresh;
    private TeacherResponse teacher;
    private TenantResponse tenant;
}