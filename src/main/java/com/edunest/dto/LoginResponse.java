package com.edunest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponse {
    private Long teacherId;
    private Integer tenantId;
    private Integer roleId;
    private String username;
    private String employeeCode;
    private String email;
    private String firstName;
    private String lastName;
    private String token;
}