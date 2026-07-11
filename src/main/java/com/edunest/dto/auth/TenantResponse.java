package com.edunest.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {
    private Integer tenantId;
    private String tenantName;
    private String logoUrl;
    private String singleLogoUrl;
    private String primaryColor;
    private String faviconUrl;
}
