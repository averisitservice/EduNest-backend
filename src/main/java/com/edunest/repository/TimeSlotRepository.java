package com.edunest.repository;

import com.edunest.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    List<TimeSlot> findByClassIdAndTenantIdAndIsActiveTrueOrderByOrderNo(Integer classId, Integer tenantId);
}