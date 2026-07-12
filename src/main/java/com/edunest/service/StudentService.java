package com.edunest.service;


import com.edunest.dto.student.StudentListResponse;
import com.edunest.dto.student.StudentRequest;

import java.util.List;

public interface StudentService {
    List<StudentListResponse> getStudentList(Integer tenantId);

    StudentRequest getStudentById(Integer studentId, Integer tenantId);

    boolean saveStudent(Integer tenantId, Integer loginTeacherId, StudentRequest request);

    boolean deleteStudent(Integer studentId, Integer loginTeacherId);
}