package com.edunest.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String oldPassword;
    private String newPassword;
}
