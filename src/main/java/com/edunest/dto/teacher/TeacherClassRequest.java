package com.edunest.dto.teacher;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherClassRequest {
    private Integer classId;
    private Integer sectionId;
}