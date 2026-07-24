package com.edunest.repository;

import com.edunest.entity.ClassMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassMasterRepository extends JpaRepository<ClassMaster, Integer> {
    List<ClassMaster> findByTenantIdAndIsActiveTrue(Integer tenantId);

    boolean existsByClassNameAndTenantId(String className, Integer tenantId);

    long countByTenantIdAndIsActiveTrue(Integer tenantId);
}
