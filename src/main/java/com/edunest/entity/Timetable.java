package com.edunest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timetable", schema = "school")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timetable_id")
    private Integer timetableId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "class_id", nullable = false)
    private Integer classId;

    @Column(name = "section_id")
    private Integer sectionId;

    @Column(name = "working_day_id", nullable = false)
    private Integer workingDayId;

    @Column(name = "time_slot_id", nullable = false)
    private Integer timeSlotId;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "academic_year_id", nullable = false)
    private Integer academicYearId;

    @Column(name = "is_active")
    private Boolean isActive = true;
}