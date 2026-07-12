package com.edunest.repository;

import com.edunest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByTenantIdAndIsActiveTrue(Integer tenantId);

    Optional<Student> findByEmail(String email);

    Optional<Student> findByAdmissionNoAndTenantId(String admissionNo, Integer tenantId);

    @Query(value = """
            SELECT admission_no FROM auth.student
            WHERE tenant_id = :tenantId
            AND admission_no LIKE CONCAT(:year, '-%')
            ORDER BY admission_no DESC
            LIMIT 1
            """, nativeQuery = true)
    String findLastAdmissionNo(@Param("tenantId") Integer tenantId,
                               @Param("year") String year);
}
