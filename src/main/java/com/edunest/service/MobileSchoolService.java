package com.edunest.service;

import com.edunest.dto.mobile.SchoolContactResponse;

public interface MobileSchoolService {

    SchoolContactResponse getSchoolContact(Integer tenantId);
}
