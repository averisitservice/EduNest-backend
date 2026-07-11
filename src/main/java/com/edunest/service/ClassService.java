package com.edunest.service;

import com.edunest.dto.classes.ClassListResponse;
import com.edunest.dto.classes.ClassRequest;

import java.util.List;

public interface ClassService {
    List<ClassListResponse> getClassList(Integer tenantId);

    ClassRequest getClassById(Integer classId, Integer tenantId);

    boolean saveClass(Integer classId, Integer tenantId, ClassRequest request);

    boolean deleteClass(Integer classId);
}