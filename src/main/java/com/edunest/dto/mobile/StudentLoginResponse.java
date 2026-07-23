package com.edunest.dto.mobile;

import com.edunest.dto.auth.TenantResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentLoginResponse {
    private String session;
    private String refresh;
    private StudentProfileResponse student;
    private TenantResponse tenant;
}
