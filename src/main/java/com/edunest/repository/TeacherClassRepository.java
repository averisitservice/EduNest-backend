package com.edunest.repository;

import com.edunest.entity.TeacherClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherClassRepository extends JpaRepository<TeacherClass, Integer> {
    List<TeacherClass> findByTeacherIdAndTenantId(Integer teacherId, Integer tenantId);

    List<TeacherClass> findByClassIdAndSectionIdAndTenantIdAndIsActiveTrue(
            Integer classId, Integer sectionId, Integer tenantId);
}