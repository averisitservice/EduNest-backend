package com.edunest.repository;

import com.edunest.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Integer> {

    List<FeePayment> findByTenantIdAndAcademicYearIdAndStudentIdIn(
            Integer tenantId, Integer academicYearId, List<Integer> studentIds);

    List<FeePayment> findByTenantIdAndStudentIdAndAcademicYearIdOrderByPaymentDateDescFeePaymentIdDesc(
            Integer tenantId, Integer studentId, Integer academicYearId);

    long countByTenantIdAndAcademicYearId(Integer tenantId, Integer academicYearId);
}
