package com.edunest.repository;

import com.edunest.entity.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Integer> {

    Optional<StudentClass> findByStudentIdAndTenantId(Integer studentId, Integer tenantId);

    List<StudentClass> findByClassIdAndSectionIdAndAcademicYearIdAndTenantId(
            Integer classId, Integer sectionId,
            Integer academicYearId, Integer tenantId);
}