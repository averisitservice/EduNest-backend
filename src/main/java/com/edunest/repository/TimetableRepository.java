package com.edunest.repository;

import com.edunest.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Integer> {

    List<Timetable> findByClassIdAndSectionIdAndAcademicYearIdAndTenantId(
            Integer classId, Integer sectionId,
            Integer academicYearId, Integer tenantId);

    List<Timetable> findByTeacherIdAndAcademicYearIdAndTenantId(
            Integer teacherId, Integer academicYearId, Integer tenantId);

    Optional<Timetable> findByTeacherIdAndWorkingDayIdAndTimeSlotIdAndAcademicYearIdAndTenantId(
            Integer teacherId, Integer workingDayId,
            Integer timeSlotId, Integer academicYearId, Integer tenantId);

    // Find existing entry for update
    Optional<Timetable> findByClassIdAndSectionIdAndWorkingDayIdAndTimeSlotIdAndAcademicYearIdAndTenantId(
            Integer classId, Integer sectionId, Integer workingDayId,
            Integer timeSlotId, Integer academicYearId, Integer tenantId);
}