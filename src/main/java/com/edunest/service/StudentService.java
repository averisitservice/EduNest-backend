package com.edunest.service;


import com.edunest.common.PagedResponse;
import com.edunest.dto.student.StudentListResponse;
import com.edunest.dto.student.StudentRequest;

public interface StudentService {
    PagedResponse<StudentListResponse> getStudentList(
            Integer tenantId, int page, int size, String search,
            Integer classId, Integer sectionId, String sortBy, String sortDir);

    StudentRequest getStudentById(Integer studentId, Integer tenantId);

    boolean saveStudent(Integer tenantId, Integer loginTeacherId, StudentRequest request);

    boolean deleteStudent(Integer studentId, Integer loginTeacherId);
}