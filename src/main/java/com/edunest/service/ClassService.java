package com.edunest.service;

import com.edunest.dto.ClassListResponse;
import com.edunest.dto.ClassRequest;

import java.util.List;

public interface ClassService {
    List<ClassListResponse> getClassList(Integer tenantId);

    ClassRequest getClassById(Integer classId, Integer tenantId);

    boolean saveClass(Integer classId, Integer tenantId, ClassRequest request);

    boolean deleteClass(Integer classId);
}