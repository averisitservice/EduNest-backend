package com.edunest.repository;

import com.edunest.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByEmail(String email);

    List<Teacher> findByTenantIdAndIsActiveTrueAndTeacherIdNot(Integer tenantId, Integer teacherId);

    long countByTenantIdAndIsActiveTrue(Integer tenantId);
}
