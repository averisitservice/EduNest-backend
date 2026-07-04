package com.edunest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher_class", schema = "school")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_class_id")
    private Integer teacherClassId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "teacher_id", nullable = false)
    private Integer teacherId;

    @Column(name = "class_id", nullable = false)
    private Integer classId;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;
}