package com.edunest.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {
    private Integer teacherId;
    private Integer roleId;
    private Integer employmentTypeId;
    private String teacherName;
    private String email;
}
