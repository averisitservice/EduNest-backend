package com.edunest.service;

import com.edunest.dto.teacher.TeacherDTO;
import com.edunest.dto.teacher.TeacherListResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherListResponse> getTeacherList(Integer tenantId, Integer teacherId);

    boolean deleteTeacher(Integer teacherId, Integer loginTeacherId);

    boolean saveTeacher(Integer teacherId, Integer tenantId, Integer loginTeacherId, TeacherDTO request);

    TeacherDTO getTeacherById(Integer teacherId);
}
