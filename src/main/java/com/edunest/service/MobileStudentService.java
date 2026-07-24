package com.edunest.service;

import com.edunest.dto.mobile.StudentDetailResponse;

public interface MobileStudentService {

    StudentDetailResponse getStudentDetailsById(Integer studentId, Integer tenantId);
}
