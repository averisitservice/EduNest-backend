package com.edunest.repository;

import com.edunest.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByTenantIdAndAcademicYearIdAndIsActiveTrueOrderByStartDateDescEventIdDesc(
            Integer tenantId, Integer academicYearId);

    List<Event> findByTenantIdAndAcademicYearIdAndIsActiveTrueAndStartDateBetweenOrderByStartDateAscEventIdAsc(
            Integer tenantId, Integer academicYearId, LocalDate fromDate, LocalDate toDate);

    List<Event> findTop5ByTenantIdAndAcademicYearIdAndIsActiveTrueAndStartDateGreaterThanEqualOrderByStartDateAscEventIdAsc(
            Integer tenantId, Integer academicYearId, LocalDate fromDate);
}
