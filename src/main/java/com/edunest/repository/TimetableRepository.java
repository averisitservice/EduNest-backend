package com.edunest.repository;

import com.edunest.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Integer> {

    @Query("SELECT t FROM Timetable t WHERE t.tenantId = :tenantId AND t.classId = :classId "
            + "AND t.academicYearId = :academicYearId "
            + "AND ((:sectionId IS NULL AND t.sectionId IS NULL) OR t.sectionId = :sectionId)")
    List<Timetable> findCells(
            @Param("classId") Integer classId, @Param("sectionId") Integer sectionId,
            @Param("academicYearId") Integer academicYearId, @Param("tenantId") Integer tenantId);

    List<Timetable> findByTeacherIdAndAcademicYearIdAndTenantId(
            Integer teacherId, Integer academicYearId, Integer tenantId);

    Optional<Timetable> findByTeacherIdAndWorkingDayIdAndTimeSlotIdAndAcademicYearIdAndTenantId(
            Integer teacherId, Integer workingDayId,
            Integer timeSlotId, Integer academicYearId, Integer tenantId);

    @Query("SELECT t FROM Timetable t WHERE t.tenantId = :tenantId AND t.classId = :classId "
            + "AND t.workingDayId = :workingDayId AND t.timeSlotId = :timeSlotId "
            + "AND t.academicYearId = :academicYearId "
            + "AND ((:sectionId IS NULL AND t.sectionId IS NULL) OR t.sectionId = :sectionId)")
    Optional<Timetable> findCell(
            @Param("classId") Integer classId, @Param("sectionId") Integer sectionId,
            @Param("workingDayId") Integer workingDayId, @Param("timeSlotId") Integer timeSlotId,
            @Param("academicYearId") Integer academicYearId, @Param("tenantId") Integer tenantId);
}
