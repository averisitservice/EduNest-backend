package com.edunest.repository;

import com.edunest.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Integer> {
    List<TeacherSubject> findByTeacherIdAndTenantId(Integer teacherId, Integer tenantId);

    List<TeacherSubject> findBySubjectIdAndTenantIdAndIsActiveTrue(Integer subjectId, Integer tenantId);
}