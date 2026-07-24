package com.edunest.repository;

import com.edunest.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Integer> {

    List<FeePayment> findByTenantIdAndAcademicYearIdAndStudentIdIn(
            Integer tenantId, Integer academicYearId, List<Integer> studentIds);

    List<FeePayment> findByTenantIdAndStudentIdAndAcademicYearIdOrderByPaymentDateDescFeePaymentIdDesc(
            Integer tenantId, Integer studentId, Integer academicYearId);

    long countByTenantIdAndAcademicYearId(Integer tenantId, Integer academicYearId);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FeePayment f "
            + "WHERE f.tenantId = :tenantId AND f.academicYearId = :academicYearId "
            + "AND f.paymentDate BETWEEN :fromDate AND :toDate")
    BigDecimal sumAmountBetween(@Param("tenantId") Integer tenantId,
                                @Param("academicYearId") Integer academicYearId,
                                @Param("fromDate") LocalDate fromDate,
                                @Param("toDate") LocalDate toDate);
}
