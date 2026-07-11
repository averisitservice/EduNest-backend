package com.edunest.dto.classes;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClassSectionResponse {
    private Integer classId;
    private String className;
    private Integer sectionId;
    private String sectionName;
    private Boolean isActive;

}
