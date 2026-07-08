package com.edunest.repository;

import com.edunest.entity.ClassFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassFeeRepository extends JpaRepository<ClassFee, Integer> {
    ClassFee findByClassIdAndAcademicYearIdAndTenantId(Integer classId, Integer academicYearId, Integer tenantId);
}