package com.edunest.dto.mobile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * School contact details for the mobile "School Contacts" screen.
 *
 * Deliberately kept out of {@code SchoolLookupResponse}: that endpoint is public
 * (pre-login) and must not expose staff contact details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolContactResponse {
    private Integer tenantId;
    private String schoolName;
    private String logoUrl;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String website;
    private String address;
}
