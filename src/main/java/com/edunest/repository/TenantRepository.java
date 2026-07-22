package com.edunest.repository;

import com.edunest.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    Optional<Tenant> findBySchoolCodeIgnoreCaseAndIsActiveTrue(String schoolCode);
}
